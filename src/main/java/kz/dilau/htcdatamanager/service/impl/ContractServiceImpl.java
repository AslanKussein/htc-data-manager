package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.ContractFormType;
import kz.dilau.htcdatamanager.domain.enums.ContractTemplateType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.repository.ApplicationDepositRepository;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.DepositNumbRepository;
import kz.dilau.htcdatamanager.service.*;
import kz.dilau.htcdatamanager.util.BundleMessageUtil;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ApplicationContractRepository contractRepository;
    private final ApplicationDepositRepository depositRepository;
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationService applicationService;
    private final ResourceLoader resourceLoader;
    private final KeycloakService keycloakService;
    private final DataProperties dataProperties;
    private final EventService eventService;
    private final DepositNumbRepository depositNumbRepository;

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
    public String generateContract(String token, ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        if (!hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
        ProfileClientDto clientDto = getClientDto(application);
        UserInfoDto userInfoDto = getUserInfo(application);
        ContractFormTemplateDto contractForm;
        byte[] result;

        if (application.getOperationType().isBuy()) {
            contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.BUY.name());
        } else if (nonNull(dto.getContractTypeId())) {
            if (dto.getContractTypeId().equals(ContractType.STANDARD)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.STANDARD.name());
            } else if (dto.getContractTypeId().equals(ContractType.EXCLUSIVE)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.EXCLUSIVE.name());
            } else {
                throw BadRequestException.createTemplateException("error.contract.form.not.found");
            }

        } else {
            throw BadRequestException.createTemplateException("error.contract.type.not.defined");
        }

        result = printContract(application, dto, clientDto, userInfoDto, contractForm);

        FileInfoDto fileInfoDto = uploadToFM(token, result, dto.getContractNumber() + ".pdf");
        ApplicationContract contract = saveContract(dto, application, entityService.mapEntity(ContractStatus.class, ContractStatus.GENERATED));
        contract.setFileGuid(fileInfoDto.getUuid());
        contractRepository.save(contract);

        return fileInfoDto.getUuid();
    }

    private ProfileClientDto getClientDtobyLogin(String clientLogin) {
        List<String> userLogin = new ArrayList<>();
        userLogin.add(clientLogin);

        List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(userLogin);
        if (profileClientDtoList.isEmpty()) {
            throw BadRequestException.createTemplateExceptionWithParam("error.client.not.found", clientLogin);
        }
        return profileClientDtoList.get(0);
    }

    private ProfileClientDto getClientDto(Application application) {
        List<String> userLogin = new ArrayList<>();
        userLogin.add(application.getClientLogin());
        List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(userLogin);
        if (profileClientDtoList.isEmpty()) {
            throw BadRequestException.createTemplateExceptionWithParam("error.client.not.found", application.getClientLogin());
        }
        ProfileClientDto clientDto = profileClientDtoList.get(0);
        if (isNull(application.getCurrentAgent())) {
            throw BadRequestException.applicationAgentNotDefined(application.getId());
        }
        return clientDto;
    }

    private UserInfoDto getUserInfo(Application application) {
        if (isNull(application.getCurrentAgent())) {
            throw BadRequestException.applicationAgentNotDefined(application.getId());
        }
        UserInfoDto userInfo = keycloakService.readUserInfo(application.getCurrentAgent());
        if (isNull(userInfo)) {
            throw BadRequestException.createTemplateException("error.user.not.found");
        }
        if (isNull(userInfo.getOrganizationDto())) {
            throw BadRequestException.applicationAdentOrgNotDefined(application.getId());
        }
        return userInfo;
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
    public String generateDepositContract(String token, DepositFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        Application sellApplication = null;
        if (!hasPermission(getAuthorName(), application)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        } else if (!application.getOperationType().isBuy()) {
            throw BadRequestException.createTemplateException("error.only.purchase.application.can.deposit");
        } else if (nonNull(application.getDeposit())) {
            throw BadRequestException.createTemplateExceptionWithParam("error.deposit.exist", dto.getApplicationId().toString());
        }
        if (nonNull(dto.getSellApplicationId())) {
            sellApplication = applicationService.getApplicationById(dto.getSellApplicationId());
            if (!sellApplication.getOperationType().isSell()) {
                throw BadRequestException.createTemplateException("error.only.sell.application.can.deposit");
            } else if (sellApplication.isReservedRealProperty()) {
                throw BadRequestException.createTemplateExceptionWithParam("error.application.to.sell.deposit", dto.getSellApplicationId().toString());
            }
        }
        UserInfoDto userInfoDto = getUserInfo(application);
        ContractFormTemplateDto contractForm;
        byte[] result;

        ProfileClientDto buyerDto = getClientDto(application);
        ProfileClientDto sellerDto = null;

        if (nonNull(sellApplication)) {
            sellerDto = getClientDto(sellApplication);
        }

        if (dto.getPayTypeId().equals(PayType.DEPOSIT)) {
            contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.DEPOSIT.name());
        } else if (dto.getPayTypeId().equals(PayType.PREPAYMENT)) {
            contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.PREPAYMENT.name());
        } else {
            throw BadRequestException.createTemplateException("error.contract.type.not.defined");
        }

        if (isNull(contractForm.getCode())) {
            throw BadRequestException.createTemplateException("error.contract.type.not.defined");
        }

        String nextNumb = getContractNextNumb(contractForm.getCode());

        result = printContractAvans(nextNumb, dto, buyerDto, sellerDto, application, sellApplication, userInfoDto, contractForm);

        FileInfoDto fileInfoDto = uploadToFM(token, result, nextNumb + ".pdf");
        saveAppDepostit(dto, application, sellApplication, nextNumb, fileInfoDto.getUuid());

        return fileInfoDto.getUuid();
    }

    private ApplicationDeposit saveAppDepostit(DepositFormDto dto,
                                               Application application,
                                               Application sellApplication,
                                               String contractNumber,
                                               String fileGuid) {
        ApplicationDeposit deposit = application.getDeposit();
        if (isNull(deposit)) {
            deposit = ApplicationDeposit.builder()
                    .application(application)
                    .build();
        }
        if (nonNull(sellApplication) && nonNull(sellApplication.getApplicationSellData()) && nonNull(sellApplication.getApplicationSellData().getRealProperty())) {
            sellApplication.getApplicationSellData().getRealProperty().setIsReserved(true);
            applicationRepository.save(sellApplication);
        }
        deposit.setSellApplication(sellApplication);
        deposit.setPayType(entityService.mapRequiredEntity(PayType.class, dto.getPayTypeId()));
        deposit.setPayedSum(dto.getPayedSum());
        deposit.setPayedClientLogin(getAuthorName());
        if (nonNull(contractNumber)) deposit.setContractNumber(contractNumber);
        if (nonNull(fileGuid)) deposit.setFileGuid(fileGuid);

        /*boolean hasStatus = false;
        for (val history : application.getStatusHistoryList()) {
            if (history.getApplicationStatus().isDeposit()) {
                hasStatus = true;
                break;
            }
        }*/

        depositRepository.save(deposit);

        //if (!hasStatus) {
        ApplicationStatus applicationStatus = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.DEPOSIT);
        application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                .application(application)
                .applicationStatus(applicationStatus)
                .build());
        application.setApplicationStatus(applicationStatus);
        application.setDeposit(deposit);
        applicationRepository.save(application);
        //}
        return deposit;
    }

    @Override
    public ClientAppContractResponseDto generateClientAppContract(String token, ClientAppContractRequestDto clientAppContractRequestDto) {
        if (isNull(clientAppContractRequestDto.getSellApplicationId())) {
            throw BadRequestException.createRequiredIsEmpty("selltApplicationId");
        }

        if (isNull(clientAppContractRequestDto.getApplicationId())) {
            throw BadRequestException.createRequiredIsEmpty("applicationId");
        }

        String currentUser = getAuthorName();

        Application currentApp = applicationService.getApplicationById(clientAppContractRequestDto.getApplicationId());

        if (!(currentApp.getApplicationStatus().getId().equals(ApplicationStatus.FIRST_CONTACT) ||
                currentApp.getApplicationStatus().getId().equals(ApplicationStatus.CONTRACT) ||
                currentApp.getApplicationStatus().getId().equals(ApplicationStatus.MEETING) ||
                currentApp.getApplicationStatus().getId().equals(ApplicationStatus.DEMO))) {
            ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.DEPOSIT);
            throw BadRequestException.createChangeStatus(currentApp.getApplicationStatus().getCode(), status.getCode());
        }

        if (!hasPermission(currentUser, currentApp)) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }

        if (!currentApp.getOperationType().isBuy()) {
            throw BadRequestException.createTemplateException("error.only.sell.application.can.buy");
        }

        Application sellApp = applicationService.getApplicationById(clientAppContractRequestDto.getSellApplicationId());

        if (!sellApp.getOperationType().isSell()) {
            throw BadRequestException.createTemplateException("error.only.sell.application.can.buy");
        } else if (sellApp.isReservedRealProperty()) {
            throw BadRequestException.createTemplateExceptionWithParam("error.deposit.exist", clientAppContractRequestDto.getSellApplicationId().toString());
        }

        ProfileClientDto ClientDto = getClientDtobyLogin(currentUser);
        UserInfoDto userInfoDto = getUserInfo(currentApp);

        ContractFormTemplateDto contractForm;
        if (clientAppContractRequestDto.getPayTypeId().equals(PayType.BUY_THREE_PRC)) {
            contractForm = keycloakService.getContractForm(
                    userInfoDto.getOrganizationDto().getId(),
                    ContractFormType.KP_BUY.name());
        } else if (clientAppContractRequestDto.getPayTypeId().equals(PayType.BOOKING)) {
            contractForm = keycloakService.getContractForm(
                    userInfoDto.getOrganizationDto().getId(),
                    ContractFormType.KP_BOOKING.name());
        } else {
            throw BadRequestException.createTemplateException("error.contract.type.not.defined");
        }

        if (isNull(contractForm.getCode())) {
            throw BadRequestException.createTemplateException("error.contract.type.not.defined");
        }
        String nextNumb = getContractNextNumb(contractForm.getCode());

        ContractFormDto dto = new ContractFormDto();
        dto.setContractNumber(nextNumb);
        dto.setContractSum(clientAppContractRequestDto.getPayedSum());
        dto.setContractTypeId(clientAppContractRequestDto.getPayTypeId());

        byte[] baos = printContract(sellApp, dto, ClientDto, userInfoDto, contractForm);

        ClientAppContractResponseDto responseDto = new ClientAppContractResponseDto();
        if (clientAppContractRequestDto.getToSave()) {
            FileInfoDto fileInfoDto = uploadToFM(token, baos, nextNumb + ".pdf");
            saveAppDepostit(clientAppContractRequestDto, currentApp, sellApp, nextNumb, fileInfoDto.getUuid());
            responseDto.setSourceStr(fileInfoDto.getUuid());
            responseDto.setSourceType("guid");
            //todo какое то уведомление нужно отправить агенту продавца
        } else {
            responseDto.setSourceStr(Base64.encodeBase64String(baos));
            responseDto.setSourceType("base64");
        }
        return responseDto;
    }

    private FileInfoDto uploadToFM(String token, byte[] baos, String filename) {
        FileInfoDto fileInfoDto;
        try {
            fileInfoDto = keycloakService.uploadFile(token, baos, filename);
            if (isNull(fileInfoDto) || isNull(fileInfoDto.getUuid()))
                throw BadRequestException.createTemplateException("error.contract.save.to.file.manager");
        } catch (Exception e) {
            e.printStackTrace();
            throw BadRequestException.createTemplateException("error.contract.save.to.file.manager");
        }
        return fileInfoDto;
    }


    private byte[] printContractAvans(String nextNumb,
                                      DepositFormDto formDto,
                                      ProfileClientDto buyerDto,
                                      ProfileClientDto sellerDto,
                                      Application appBuy,
                                      Application appSell,
                                      UserInfoDto userInfo,
                                      ContractFormTemplateDto contractForm) {
        try {
            InputStream logoImage = null;
            InputStream footerImage = null;
            List<JasperPrint> jasperPrintList = new ArrayList<>();

            List<ContractTempaleDto> templateList = contractForm.getTemplateList();
            ContractTempaleDto logoPath = getTemplateByName(ContractTemplateType.LOGO.name(), templateList);
            ContractTempaleDto logoFooterPath = getTemplateByName(ContractTemplateType.FOOTER_LOGO.name(), templateList);

            if (nonNull(logoPath) && nonNull(logoPath.getTemplate())) {
                logoImage = getLogo(logoPath.getTemplate());
            }
            if (nonNull(logoFooterPath) && nonNull(logoFooterPath.getTemplate())) {
                footerImage = getLogo(logoFooterPath.getTemplate());
            }

            if (templateList.isEmpty()) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            for (ContractTempaleDto tpl : templateList) {
                if (tpl.getName().equals(ContractTemplateType.LOGO.name()) || tpl.getName().equals(ContractTemplateType.FOOTER_LOGO.name())) {
                    continue;
                }

                if (nonNull(tpl.getParList())) {
                    Map<String, Object> pars = getBindParAvans(nextNumb,
                            tpl,
                            formDto,
                            buyerDto,
                            sellerDto,
                            appBuy,
                            appSell,
                            userInfo,
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
        } catch (Exception e) {
            e.printStackTrace();
            throw BadRequestException.createTemplateException("error.contract.forming");
        }
    }

    private Map<String, Object> getBindParAvans(String nextNumb,
                                                ContractTempaleDto tpl,
                                                DepositFormDto formDto,
                                                ProfileClientDto buyerDto,
                                                ProfileClientDto sellerDto,
                                                Application appBuy,
                                                Application appSell,
                                                UserInfoDto userInfoDto,
                                                InputStream logoImage,
                                                InputStream footerImage) {
        Map<String, Object> pars = new HashMap<>();

        RealProperty realProperty = null;

        if (nonNull(appSell)) {
            realProperty = nonNull(appSell.getApplicationSellData()) ? appSell.getApplicationSellData().getRealProperty() : null;
        }

        District district = nonNull(realProperty) && nonNull(realProperty.getBuilding()) ? realProperty.getBuilding().getDistrict() : null;

        for (String par : tpl.getParList()) {
            switch (par) {
                case "logoImage":
                    pars.put(par, logoImage);
                    break;
                case "footerImage":
                    pars.put(par, footerImage);
                    break;
                case "city":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getBuilding()) && nonNull(realProperty.getBuilding().getCity()) ? realProperty.getBuilding().getCity().getMultiLang().getNameRu() : "");
                    break;
                case "printDate":
                case "docDate":
                    pars.put(par, sdfDate.format(new Date()));
                    break;
                case "docNumb":
                    pars.put(par, nextNumb);
                    break;
                case "contractNumber":
                    pars.put(par, nonNull(appBuy) && nonNull(appBuy.getContract()) ? appBuy.getContract().getContractNumber() : "");
                    break;
                case "contractDate":
                    if (nonNull(appBuy) && nonNull(appBuy.getContract())) {
                        pars.put(par, dtfDate.format(appBuy.getContract().getCreatedDate()));
                    } else {
                        pars.put(par, "");
                    }
                    break;
                case "comissionAmount":
                    pars.put(par, nonNull(appBuy) && nonNull(appBuy.getContract()) ? appBuy.getContract().getCommission().toString() : "");
                    break;
                case "buyerFullname":
                    pars.put(par, buyerDto.getFullname());
                    break;
                case "sellerFullname":
                    pars.put(par, nonNull(sellerDto) ? sellerDto.getFullname() : "");
                    break;
                case "agentFullname":
                    pars.put(par, userInfoDto.getFullname());
                    break;
                case "handselAmount":
                    pars.put(par, nonNull(formDto.getPayedSum()) ? formDto.getPayedSum().toString() : "");
                    break;
                case "objectPrice":
                    pars.put(par, nonNull(appSell) && nonNull(appSell.getApplicationSellData()) ? appSell.getApplicationSellData().getObjectPrice().toString() : "");
                    break;
                case "objectFullAddress":
                    pars.put(par, nonNull(realProperty) ? DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber()).getNameRu() : "");
                    break;
                case "objectRCName":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getBuilding()) && nonNull(realProperty.getBuilding().getResidentialComplex()) ? realProperty.getBuilding().getResidentialComplex().getHouseName() : "");
                    break;
                case "objectRegion":
                    pars.put(par, nonNull(district) ? district.getMultiLang().getNameRu() : "");
                case "objectStreet":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getBuilding()) && nonNull(realProperty.getBuilding().getStreet()) ? realProperty.getBuilding().getStreet().getMultiLang().getNameRu() : "");
                    break;
                case "objectHouseNumber":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getBuilding()) && nonNull(realProperty.getBuilding().getHouseNumber()) ? realProperty.getBuilding().getHouseNumber() : "" + (nonNull(realProperty.getBuilding().getHouseNumberFraction()) ? "/" + realProperty.getBuilding().getHouseNumberFraction() : ""));
                    break;
                case "objectApartmentNumber":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getApartmentNumber()) ? realProperty.getApartmentNumber() : "");
                    break;
                default:
                    pars.put(par, "");
                    break;
            }
        }
        return pars;
    }

    private Map<String, Object> getBindPars(
            ContractTempaleDto tpl,
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
            realPropertyMetadata = isNull(realProperty) ? null : realProperty.getMetadataByStatus(MetadataStatus.APPROVED);

            if (nonNull(realProperty.getBuilding())) {
                city = realProperty.getBuilding().getCity();
                district = realProperty.getBuilding().getDistrict();
            }
        }

        for (String par : tpl.getParList()) {
            switch (par) {
                case "logoImage":
                    pars.put(par, logoImage);
                    break;
                case "footerImage":
                    pars.put(par, footerImage);
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
                case "clientDocOrg":
                    pars.put(par, nonNull(clientDto.getDocOrg()) ? clientDto.getDocOrg() : "");
                    break;
                case "clientDocDate":
                    pars.put(par, nonNull(clientDto.getDocDate()) ? dtfDate.format(clientDto.getDocDate()) : "");
                    break;
                case "clientAddress":
                    pars.put(par, nonNull(clientDto.getDocDate()) ? clientDto.getAddress() : "");
                    break;
                case "clientDocNumb":
                    pars.put(par, nonNull(clientDto.getDocNumber()) ? clientDto.getDocNumber() : "");
                    break;
                case "clientEmail":
                    pars.put(par, nonNull(clientDto.getEmail()) ? clientDto.getEmail() : "");
                    break;
                case "clientIIN":
                    pars.put(par, nonNull(clientDto.getIin()) ? clientDto.getIin() : "");
                    break;
                case "agentFullname":
                    pars.put(par, userInfoDto.getFullname());
                    break;
                case "objectFullAddress":
                    pars.put(par, nonNull(realProperty) ? DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber()).getNameRu() : "");
                    break;
                case "objectRCName":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getBuilding()) && nonNull(realProperty.getBuilding().getResidentialComplex()) ? realProperty.getBuilding().getResidentialComplex().getHouseName() : "");
                    break;
                case "objectRegion":
                    pars.put(par, nonNull(district) ? district.getMultiLang().getNameRu() : "");
                    break;
                case "objectType":
                    pars.put(par, application.getObjectType().getMultiLang().getNameRu());
                    break;
                case "objectRoomCount":
                    if (application.getOperationType().isBuy()) {
                        pars.put(par, nonNull(purchaseInfo) && nonNull(purchaseInfo.getNumberOfRoomsFrom()) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
                    } else {
                        pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getNumberOfRooms().toString() : "");
                    }
                    break;
                case "objectBathroomType":
                case "objectBathroomTypeRU":
                case "objectBathroomTypeKZ":
                    String locale = "ru";
                    if (par.equals("objectBathroomTypeKZ")) {
                        locale = "kz";
                    }
                    if (application.getOperationType().isSell()) {
                        if (nonNull(realPropertyMetadata.getSeparateBathroom())) {
                            pars.put(par, realPropertyMetadata.getSeparateBathroom() ? getTmplMsg("template.contract.bathroomtype.split", locale) : getTmplMsg("template.contract.bathroomtype.single", locale));
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
                        pars.put(par, nonNull(realPropertyMetadata) ? realPropertyMetadata.getTotalArea().toString() : "");
                    }
                    break;
                case "objectKitchenArea":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getKitchenArea()) ? realPropertyMetadata.getKitchenArea().toString() : "");
                    break;
                case "objectLivingArea":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getLivingArea()) ? realPropertyMetadata.getLivingArea().toString() : "");
                    break;
                case "objectReadyYear":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getGeneralCharacteristics()) && nonNull(realPropertyMetadata.getGeneralCharacteristics().getYearOfConstruction()) ? realPropertyMetadata.getGeneralCharacteristics().getYearOfConstruction().toString() : "");
                    break;
                case "objectCadastralNumber":
                    pars.put(par, nonNull(realProperty) && nonNull(realProperty.getCadastralNumber()) ? realProperty.getCadastralNumber() : "");
                    break;
                case "objectCollaterial":
                    if (nonNull(sellData)) {
                        pars.put(par, nonNull(sellData.getEncumbrance()) && sellData.getEncumbrance() ? getTmplMsg("template.contract.yes", "ru") : getTmplMsg("template.contract.no", "ru"));
                    } else {
                        pars.put(par, "");
                    }
                    break;
                case "objectFloor":
                    if (application.getOperationType().isBuy()) {
                        pars.put(par, nonNull(purchaseInfo) && nonNull(purchaseInfo.getFloorFrom()) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");
                    } else {
                        pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getFloor()) ? realPropertyMetadata.getFloor().toString() : "");
                    }
                    break;
                case "objectFloorTotal":
                    pars.put(par, nonNull(realPropertyMetadata) && nonNull(realPropertyMetadata.getGeneralCharacteristics()) && nonNull(realPropertyMetadata.getGeneralCharacteristics().getNumberOfFloors()) ? realPropertyMetadata.getGeneralCharacteristics().getNumberOfFloors().toString() : "");
                    break;
                case "contractSum":
                    pars.put(par, nonNull(dto.getContractSum()) ? dto.getContractSum().toString() : "");
                    break;
                case "contractSum3Prc":
                    pars.put(par, nonNull(dto.getContractSum()) ? String.valueOf(dto.getContractSum().floatValue() * 0.03) : "");
                    break;
                case "objectPrice":
                    if (nonNull(purchaseInfo)) {
                        pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getObjectPriceFrom() + " - " + purchaseInfo.getObjectPriceTo() : "");
                    } else {
                        pars.put(par, nonNull(sellData) ? sellData.getObjectPrice().toString() : "");
                    }
                    break;
                case "objectPrice3Prc":
                    pars.put(par, nonNull(sellData) ? String.valueOf(sellData.getObjectPrice().floatValue() * 0.03) : "");
                    break;
                case "objectMaxPrice":
                    pars.put(par, nonNull(purchaseInfo) ? purchaseInfo.getObjectPriceTo().toString() : "");
                    break;
                case "docNumb":
                case "contractNumber":
                    pars.put(par, nonNull(dto) ? dto.getContractNumber() : "");
                    break;
                case "CollectionBeanParam":
                case "CollectionPerspectivaBuyActView":
                    List<JasperActViewDto> ev;
                    if (application.getOperationType().isBuy()) {
                        ev = eventService.getViewbyTargetApp(dto.getApplicationId());
                    } else {
                        ev = eventService.getViewBySourceApp(dto.getApplicationId());
                    }
                    if (ev.isEmpty()) {
                        ev.add(new JasperActViewDto("", "", ""));
                    }
                    JRBeanCollectionDataSource parDS = new JRBeanCollectionDataSource(ev);
                    pars.put(par, parDS);
                    break;
                default:
                    pars.put(par, "");
                    break;
            }
        }
        return pars;
    }

    private String getTmplMsg(String name, String locale) {
        return BundleMessageUtil.getMessage(name, new Locale(locale));
    }

    private byte[] printContract(Application application,
                                 ContractFormDto dto,
                                 ProfileClientDto clientDto,
                                 UserInfoDto userInfoDto,
                                 ContractFormTemplateDto contractForm) {
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

            ContractTempaleDto logoPath = getTemplateByName(ContractTemplateType.LOGO.name(), templateList);
            ContractTempaleDto logoFooterPath = getTemplateByName(ContractTemplateType.FOOTER_LOGO.name(), templateList);

            if (nonNull(logoPath) && nonNull(logoPath.getTemplate())) {
                logoImage = getLogo(logoPath.getTemplate());
            }
            if (nonNull(logoFooterPath) && nonNull(logoFooterPath.getTemplate())) {
                footerImage = getLogo(logoFooterPath.getTemplate());
            }

            if (templateList.isEmpty()) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            for (ContractTempaleDto tpl : templateList) {
                if (tpl.getName().equals(ContractTemplateType.LOGO.name()) || tpl.getName().equals(ContractTemplateType.FOOTER_LOGO.name())) {
                    continue;
                }

                if (nonNull(tpl.getParList())) {
                    Map<String, Object> pars = getBindPars(
                            tpl,
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
        } catch (Exception e) {
            e.printStackTrace();
            throw BadRequestException.createTemplateException("error.contract.forming");
        }
    }

    private ContractTempaleDto getTemplateByName(String name, List<ContractTempaleDto> templateList) {
        return templateList.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private InputStream getLogo(String path) {
        return getClass().getResourceAsStream(path);
    }

    private InputStream getJrxml(ContractTempaleDto tpl) {
        return new ByteArrayInputStream(tpl.getTemplate().getBytes(StandardCharsets.UTF_8));
    }

    private byte[] getPages(List<JasperPrint> jasperPrintList) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        //Add the list as a Parameter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        //this will make a bookmark in the exported PDF for each of the reports
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
        return baos.toByteArray();//Base64.encodeBase64String(baos.toByteArray());
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

    private String getContractNextNumb(String formCode) {
        if (isNull(formCode)) {
            throw BadRequestException.createTemplateException("error.contract.form.not.found");
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");

        StringBuilder codeStr = new StringBuilder(formCode);
        codeStr.append("-");
        codeStr.append(fmt.format(new Date()));

        DepositNumb dn = depositNumbRepository.findByCode(codeStr.toString()).orElse(null);

        if (isNull(dn)) {
            dn = new DepositNumb();
            dn.setCode(codeStr.toString());
            dn.setNmb(1L);
        } else {
            dn.setNmb(dn.getNmb() + 1L);
        }

        depositNumbRepository.saveAndFlush(dn);

        String str = "000" + String.valueOf(dn.getNmb().intValue());
        codeStr.append((str).substring(Math.max(str.length() - 4, 0)));

        return codeStr.toString();
    }

    private ApplicationContract saveContract(ContractFormDto dto, Application application, ContractStatus status) {
        ApplicationContract contract = application.getContract();
        if (isNull(contract)) {
            contract = ApplicationContract.builder()
                    .application(application)
                    .build();
        }
        contract.setCommission(BigDecimal.valueOf(getCommission(dto.getContractSum().intValue(), application.getObjectTypeId())));
        contract.setContractSum(dto.getContractSum());
        contract.setContractPeriod(dto.getContractPeriod());
        contract.setContractNumber(dto.getContractNumber());
        contract.setPrintDate(ZonedDateTime.now());
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
