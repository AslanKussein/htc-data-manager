package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.CommissionRange;
import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.ContractFormType;
import kz.dilau.htcdatamanager.domain.enums.ContractTemplateType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.service.*;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");

    private final ApplicationContractRepository contractRepository;
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationService applicationService;
    private final ResourceLoader resourceLoader;
    private final KeycloakService keycloakService;
    private final DataProperties dataProperties;
    private final EventService eventService;

    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Override
    public ContractFormDto getContractForm(String token, Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        if (!hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
        return new ContractFormDto(application.getContract());
    }

    private boolean hasPermission(String authorName, Application application) {
        return nonNull(authorName) && nonNull(application) && (authorName.equalsIgnoreCase(application.getCreatedBy()) || nonNull(application.getCurrentAgent()) && authorName.equalsIgnoreCase(application.getCurrentAgent()));
    }

    @Override
    public String generateContract(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        if (!hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
        List<String> userLogin = new ArrayList<>();
        userLogin.add(application.getClientLogin());
        List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(userLogin);
        if (profileClientDtoList.isEmpty()) {
            throw BadRequestException.createTemplateException("error.application.contract");
        }
        ProfileClientDto clientDto = profileClientDtoList.get(0);
        if (isNull(application.getCurrentAgent())) {
            throw BadRequestException.createTemplateException("error.user.not.found");
        }
        userLogin.clear();
        userLogin.add(application.getCurrentAgent());
        ListResponse<UserInfoDto> userInfos = keycloakService.readUserInfos(userLogin);
        if (isNull(userInfos) || isNull(userInfos.getData()) || userInfos.getData().isEmpty()) {
            throw BadRequestException.createTemplateException("error.application.contract");
        }
        UserInfoDto userInfoDto = userInfos.getData().get(0);
        if (isNull(userInfoDto)) {
            throw BadRequestException.createTemplateException("error.user.not.found");
        }
        if (isNull(userInfoDto.getOrganizationDto())) {
            throw BadRequestException.createTemplateException("error.contract.form.not.found");
        }
        ContractFormTemplateDto contractForm;
        String result = null;

        if (application.getOperationType().isBuy()) {
            contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.BUY.name());
            result = printContract(application, dto, clientDto, userInfoDto, contractForm);
        } else if (nonNull(dto.getContractTypeId())) {
            if (dto.getContractTypeId().equals(ContractType.STANDARD)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.STANDARD.name());
            } else if (dto.getContractTypeId().equals(ContractType.EXCLUSIVE)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.EXCLUSIVE.name());
            } else {
                throw BadRequestException.createTemplateException("error.contract.form.not.found");
            }
            result = printContract(application, dto, clientDto, userInfoDto, contractForm);
        }

        if (nonNull(result)) {
            saveContract(dto, application, entityService.mapEntity(ContractStatus.class, ContractStatus.GENERATED));
        }
        return result;
    }

    private ContractFormTemplateDto getContractForm(Long organizationId, String contractType) {
        ContractFormTemplateDto contractForm = keycloakService.getContractForm(organizationId, contractType);
        if (nonNull(contractForm)) {
            return contractForm;
        } else {
            throw BadRequestException.createTemplateException("error.contract.form.not.found");
        }
    }

    @Override
    public String generateContractHandsel(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());

        if (hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }

        return printContractHandsel(application, dto, dto.getGuid());
    }

    @Override
    public String generateContractAvans(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());

        if (hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }

        return printContractAvans(application, dto, dto.getGuid());
    }

    private String printContractAvans(Application application, ContractFormDto dto, String orgName) {

        try {
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            City city = purchaseData.getCity();

            Resource resource = resourceLoader.getResource("classpath:jasper/handsel/" + orgName + "/avans/main.jrxml");
            InputStream input = resource.getInputStream();
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image = getLogo(""); //=
            mainPar.put("logoImage", image);
            mainPar.put("contractNumber", dto.getContractNumber());
            mainPar.put("contractDate", sdfDate.format(new Date()));
            mainPar.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            mainPar.put("printDate", sdfDate.format(new Date()));
            mainPar.put("buyerFullname", "Buyer Fullname");
            //mainPar.put("sellerFullname", "Seller Fullname");
            mainPar.put("objectFullAddress", "Nur Sultan, Mangilik El");
            mainPar.put("objectPrice", "10 000 000");
            mainPar.put("handselAmount", "200 000");
            mainPar.put("docExpireDate", "20.12.2020");
            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, mainPar, new JREmptyDataSource());

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            return getPages(jasperPrintList);

        } catch (Exception e) {
            //e.printStackTrace();
            return e.getMessage();
        }
    }


    private String printContractHandsel(Application application, ContractFormDto dto, String orgName) {

        try {
            //String orgName = "vitrina";

            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();

            Resource resource = resourceLoader.getResource("classpath:jasper/handsel/" + orgName + "/main.jrxml");
            InputStream input = resource.getInputStream();
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image = getLogo(""); //=
            mainPar.put("logoImage", image);
            mainPar.put("contractNumber", dto.getContractNumber());
            mainPar.put("contractDate", sdfDate.format(new Date()));
            mainPar.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            mainPar.put("printDate", sdfDate.format(new Date()));
            mainPar.put("buyerFullname", "Buyer Fullname");
            mainPar.put("sellerFullname", "Seller Fullname");
            mainPar.put("objectFullAddress", "Nur Sultan, Mangilik El");
            mainPar.put("objectPrice", "10 000 000");
            mainPar.put("handselAmount", "200 000");
            mainPar.put("docExpireDate", "20.12.2020");
            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, mainPar, new JREmptyDataSource());
            //--------------------------
            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/handsel/" + orgName + "/duties.jrxml");

            InputStream inputDuties = resourceDuties.getInputStream();
            Map<String, Object> dutiesPar = new HashMap<>();
            dutiesPar.put("comissionAmount", "300 000");
            dutiesPar.put("sellerFullname", "Seller Fullname");
            dutiesPar.put("buyerFullname", "Buyer Fullname");

            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());
            //--------------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/handsel/" + orgName + "/recvisit.jrxml");

            InputStream inputRecv = resourceRecv.getInputStream();
            Map<String, Object> recvPar = new HashMap<>();
            recvPar.put("agentFullname", "Agent Fullname");

            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
            //--------------------------
            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintRecv);
            return getPages(jasperPrintList);

        } catch (Exception e) {
            //e.printStackTrace();
            return e.getMessage();
        }
    }

    private Map<String, Object> getBindPars(ContractTempaleDto tpl,
                                            ApplicationPurchaseData purchaseData,
                                            ApplicationSellData sellData,
                                            ProfileClientDto clientDto,
                                            UserInfoDto userInfoDto,
                                            Application application,
                                            ContractFormDto dto,
                                            InputStream logoImage,
                                            InputStream footerImage) {
        Map<String, Object> pars = new HashMap<>();
        City city = null;
        District district = null;
        PurchaseInfo purchaseInfo = null;

        RealProperty realProperty = null;
        RealPropertyMetadata realPropertyMetadata = null;

        if (application.getOperationType().isBuy()) {
            city = isNull(purchaseData) ? null : purchaseData.getCity();
            district = isNull(purchaseData) ? null : purchaseData.getDistrict();
            purchaseInfo = isNull(purchaseData) ? null : purchaseData.getPurchaseInfo();
        } else {
            realProperty = isNull(sellData) ? null : sellData.getRealProperty();
            realPropertyMetadata = isNull (realProperty) ? null : realProperty.getMetadataByStatus(MetadataStatus.APPROVED);

            if (nonNull(realProperty.getBuilding())) {
                city = realProperty.getBuilding().getCity();
                district = realProperty.getBuilding().getDistrict();
            }
        }

        for ( String par : tpl.getParList()) {
            switch (par) {
                case "logoImage":
                    pars.put(par, logoImage);
                    break;
                case "city":
                case "cityRU":
                    pars.put(par, nonNull(city) ? city.getMultiLang().getNameRu() : "");
                    break;
                case "cityKZ":
                    pars.put(par, nonNull(city) ? city.getMultiLang().getNameKz() : "");
                    break;
                case "printDate":
                case "docDate":
                    pars.put(par, sdfDate.format(new Date()));
                    break;
                case "clientFullname":
                    pars.put(par, clientDto.getFullname());
                    break;
                case "clientMobilePhone":
                    pars.put(par, clientDto.getPhoneNumber());
                    break;
                case "agentFullname":
                    pars.put(par, userInfoDto.getFullname());
                    break;
                case "objectFullAddress":
                    pars.put(par, nonNull(realProperty) ? DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber()).getNameRu() : "");
                    break;
                case "objectRCName":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getResidentialComplex()) ? realPropertyMetadata.getResidentialComplex().getHouseName() : "");
                    break;
                case "objectRegion":
                    pars.put(par, nonNull(district) ? district.getMultiLang().getNameRu() : "");
                    break;
                case "objectType":
                    pars.put(par, application.getObjectType().getMultiLang().getNameRu());
                    break;
                case "objectRoomCount":
                    if (application.getOperationType().isBuy()) {
                        pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
                    } else {
                        pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getNumberOfRooms() : "");
                    }
                    break;
                case "objectBathroomType":
                case "objectBathroomTypeRU":
                case "objectBathroomTypeKZ":
                    if (application.getOperationType().isSell()) {
                        if (nonNull(realPropertyMetadata.getSeparateBathroom())) {
                            pars.put(par, realPropertyMetadata.getSeparateBathroom() ? "Раздельный" : "совмещенный");
                        } else {
                            pars.put(par, "");
                        }
                    } else {
                        pars.put(par, "");
                    }
                    break;
                case "objectArea":
                    if (application.getOperationType().isBuy()) {
                        pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
                    } else {
                        pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getTotalArea() : "");
                    }
                    break;
                case "objectKitchenArea":
                    pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getKitchenArea() : "");
                    break;
                case "objectLivingArea":
                    pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getLivingArea() : "");
                    break;
                case "objectReadyYear":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getGeneralCharacteristics()) ? realPropertyMetadata.getGeneralCharacteristics().getYearOfConstruction().toString() : "");
                    break;
                case "objectCadastralNumber":
                    pars.put(par, nonNull(realProperty) ? realProperty.getCadastralNumber() : "");
                    break;
                case "objectCollaterial":
                    if (nonNull(sellData)) {
                        pars.put(par, sellData.getEncumbrance() ? "Да" : "Нет");
                    } else {
                        pars.put(par, "");
                    }
                    break;
                case "objectFloor":
                    if (application.getOperationType().isBuy()) {
                        pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");
                    } else {
                        pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getFloor() : "");
                    }
                    break;
                case "objectFloorTotal":
                    pars.put(par, nonNull(realPropertyMetadata.getGeneralCharacteristics()) ? realPropertyMetadata.getGeneralCharacteristics().getNumberOfFloors() : "" );
                    break;
                case "contractSum":
                case "objectPrice":
                    pars.put(par, nonNull(dto.getContractSum()) ? dto.getContractSum().toString() : "");
                    break;
                case "objectMaxPrice":
                    pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getObjectPriceTo().toString() : "");
                    break;
                case "docNumb":
                case "contractNumber":
                    pars.put(par, dto.getContractNumber());
                    break;
                case "CollectionBeanParam":
                case "CollectionPerspectivaBuyActView":
                    List<JasperActViewDto> ev = new ArrayList<>();
                    if (application.getOperationType().isBuy()) {
                        ev = eventService.getViewbyTargetApp(dto.getApplicationId());
                    } else {
                        ev = eventService.getViewBySourceApp(dto.getApplicationId());
                    }
                    if (ev.isEmpty()) {
                        ev.add(new JasperActViewDto("", "", ""));
                    }
                    JRBeanCollectionDataSource parDS = new JRBeanCollectionDataSource(ev);
                    pars.put("CollectionBeanParam", parDS);
                    break;
                default:
                    pars.put(par, "");
                    break;
            }
        }
        return pars;
    }

    private String printContract(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {
        try {
            if (application.getOperationType().isSell() && isNull(application.getApplicationSellData()) ||
                    application.getOperationType().isBuy() && isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }

            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            ApplicationSellData sellData = application.getApplicationSellData();
            List<ContractTempaleDto> templateList = contractForm.getTemplateList();

            InputStream logoImage = null;
            InputStream footerImage = null;
            List<JasperPrint> jasperPrintList = new ArrayList<>();

            ContractTempaleDto logoPath = templateList.stream()
                    .filter( t -> t.getName().equals(ContractTemplateType.LOGO.name()))
                    .findFirst()
                    .orElse(null);
            ContractTempaleDto logoFooterPath = templateList.stream()
                    .filter( t -> t.getName().equals(ContractTemplateType.FOOTER_LOGO.name()))
                    .findFirst()
                    .orElse(null);
            if (nonNull(logoPath) && nonNull(logoPath.getTemplate())) {
                logoImage = getLogo(logoPath.getTemplate());
            }
            if (nonNull(logoFooterPath) && nonNull(logoFooterPath.getTemplate())) {
                footerImage = getLogo(logoFooterPath.getTemplate());
            }

            if (!templateList.isEmpty() && templateList.size() > 0) {
                for (ContractTempaleDto tpl : templateList) {
                    if (tpl.getName().equals(ContractTemplateType.LOGO.name()) || tpl.getName().equals(ContractTemplateType.FOOTER_LOGO.name())) {
                        continue;
                    }

                    if (nonNull(tpl.getParList()) ) {
                        Map<String, Object> pars = getBindPars(tpl,
                                purchaseData,
                                sellData,
                                clientDto,
                                userInfoDto,
                                application,
                                dto,
                                logoImage,
                                footerImage
                                );
                        JasperReport jr = JasperCompileManager.compileReport(getJrxml(tpl));
                        JasperPrint jp = JasperFillManager.fillReport(jr, pars, new JREmptyDataSource());
                        jasperPrintList.add(jp);
                    } else {
                        JasperReport jr = JasperCompileManager.compileReport(getJrxml(tpl));
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, new JREmptyDataSource());
                        jasperPrintList.add(jp);
                    }
                }
                return getPages(jasperPrintList);
            } else {
                return "empty";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private InputStream getLogo(String path) {
        return getClass().getResourceAsStream(path);
    }

    private InputStream getJrxml(ContractTempaleDto tpl) {
        return new ByteArrayInputStream(tpl.getTemplate().getBytes(StandardCharsets.UTF_8));
    }

    private InputStream getLogoPerspective() {
        return getClass().getResourceAsStream("/jasper/logo_perspectiva.png");
    }

    private InputStream getLogoVitrina() {
        return getClass().getResourceAsStream("/jasper/logo.png");
    }

    private InputStream getFooterImagePerspective() {
        return getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
    }

    private String getPages(List<JasperPrint> jasperPrintList) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        //Add the list as a Parameter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        //this will make a bookmark in the exported PDF for each of the reports
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        return Base64.encodeBase64String(baos.toByteArray());
    }

    @Override
    public Long missContract(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        if (!hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
        if (nonNull(application.getContract()) && application.getContract().getContractStatus().isGenerated()) {
            throw BadRequestException.createTemplateException("error.contract.already.generated");
        }
        ApplicationContract contract = saveContract(dto, application, entityService.mapEntity(ContractStatus.class, ContractStatus.MISSING));
        return contract.getId();
    }

    @Override
    public Integer getCommission(Integer sum, Long objectTypeId) {
        Integer result = 0;
        if (objectTypeId.equals(ObjectType.HOUSE)) {
            result = getPercentFromSum(sum, dataProperties.getCommissionForHouse().floatValue());
        } else {
            Float amount;
            for (val commission : dataProperties.getCommissionRangeList()) {
                if (commission.getFrom() < sum && commission.getTo() >= sum) {
                    amount = commission.getAmount().floatValue();
                    if (amount < 100) {
                        result = getPercentFromSum(sum, amount);
                    } else {
                        result = amount.intValue();
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public ListResponse<CommissionRangeDto> getAllCommissions() {
        List<CommissionRangeDto> result = new ArrayList<>();
        result.add(CommissionRangeDto.builder()
                .houseAmount(dataProperties.getCommissionForHouse())
                .build());
        dataProperties.getCommissionRangeList().forEach(range -> result.add(new CommissionRangeDto(range)));
        return new ListResponse<>(result);
    }

    private Integer getPercentFromSum(Integer sum, Float percent) {
        Float result = sum * percent / 100;
        return result.intValue();
    }

    private ApplicationContract saveContract(ContractFormDto dto, Application application, ContractStatus status) {
        ApplicationContract contract = application.getContract();
        if (isNull(contract)) {
            contract = ApplicationContract.builder()
                    .application(application)
                    .build();
        }
        contract.setCommission(dto.getCommission());
        contract.setContractSum(dto.getContractSum());
        contract.setContractPeriod(dto.getContractPeriod());
        contract.setContractNumber(dto.getContractNumber());
        contract.setContractStatus(status);
        contract.setContractType(entityService.mapEntity(ContractType.class, dto.getContractTypeId()));
        boolean hasStatus = false;
        for (val history : application.getStatusHistoryList()) {
            if (history.getApplicationStatus().isContract()) {
                hasStatus = true;
                break;
            }
        }
        if (hasStatus) {
            contract = contractRepository.save(contract);
        } else {
            ApplicationStatus applicationStatus = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.CONTRACT);
            application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(applicationStatus)
                    .build());
            application.setApplicationStatus(applicationStatus);
            application.setContract(contract);
            applicationRepository.save(application);
        }
        return contract;
    }
}
