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
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperBasicDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperPerspectivaActViewDto;
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
            result = generateContractBuy(application, dto, clientDto, userInfoDto, contractForm);
        } else if (nonNull(dto.getContractTypeId())) {
            if (dto.getContractTypeId().equals(ContractType.STANDARD)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.STANDARD.name());
                result = generateContractSale(application, dto, clientDto, userInfoDto, contractForm);
            } else if (dto.getContractTypeId().equals(ContractType.EXCLUSIVE)) {
                contractForm = getContractForm(userInfoDto.getOrganizationDto().getId(), ContractFormType.EXCLUSIVE.name());
                result = generateContractSaleExclusive(application, dto, clientDto, userInfoDto, contractForm);
            }
        }

//        if (dto.getGuid().equals("perspective_sale_excl")) {
//            result = generateContractSaleExclusivePerspective(application, dto);
//        }

        /*if (dto.getGuid().equals("perspective_sale_standart")) {
            result = generateContractSaleStandartPerspective(application, dto);
        }*/

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

    private String generateContractBuy(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {
        try {
            if (application.getOperationType().isSell() || isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            List<JasperPrint> jasperPrintList = new ArrayList<>();
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Map<String, String> templateMap = contractForm.getTemplateMap();
            InputStream input = new ByteArrayInputStream(templateMap.get(ContractTemplateType.MAIN.name()).getBytes(StandardCharsets.UTF_8));

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            InputStream image = getLogo(templateMap.get(ContractTemplateType.LOGO.name())); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            parameters.put("logoImage", image);

            InputStream footerImage = null;
            if (nonNull(templateMap.get(ContractTemplateType.FOOTER_LOGO.name()))) {
                footerImage = getLogo(templateMap.get(ContractTemplateType.FOOTER_LOGO.name()));
                parameters.put("footerImage", footerImage);
            }
            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", sdfDate.format(new Date()));
            parameters.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("cityKZ", nonNull(city) ? city.getMultiLang().getNameKz() : "");
            parameters.put("cityRU", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", sdfDate.format(new Date()));
            parameters.put("clientFullname", clientDto.getFullname());

            parameters.put("objectRegion", nonNull(district) ? district.getMultiLang().getNameRu() : "");
            parameters.put("objectType", application.getObjectType().getMultiLang().getNameRu());
            parameters.put("objectRoomCount", nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
            parameters.put("objectArea", nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
            parameters.put("objectFloor", nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, parameters, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintBasic);
            //----------------------

            InputStream inputDuties = new ByteArrayInputStream(templateMap.get(ContractTemplateType.DUTIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            Map<String, Object> dutiesPar = new HashMap<>();
            if (nonNull(footerImage)) {
                dutiesPar.put("footerImage", footerImage);
            }
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintDuties);

            //--------------------
            InputStream inputPrice = new ByteArrayInputStream(templateMap.get(ContractTemplateType.PRICE.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            Map<String, Object> pricePar = new HashMap<>();
            if (nonNull(footerImage)) {
                pricePar.put("footerImage", footerImage);
            }
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, pricePar, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintPrice);

            //----------------------
            InputStream inputResp = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RESPONSIBILITIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            Map<String, Object> respPar = new HashMap<>();
            if (nonNull(footerImage)) {
                respPar.put("footerImage", footerImage);
            }
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, respPar, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintResp);

            //----------------------
            if (nonNull(templateMap.get(ContractTemplateType.DETAILS.name()))) {
                Map<String, Object> detailPar = new HashMap<>();
                List<JasperBasicDto> detailItems = new ArrayList<>();

                detailItems.add(new JasperBasicDto("-", "-"));
                JRBeanCollectionDataSource detailDs = new JRBeanCollectionDataSource(detailItems);
                detailPar.put("CollectionBeanParam", detailDs);
                InputStream inputDetail = new ByteArrayInputStream(templateMap.get(ContractTemplateType.DETAILS.name()).getBytes(StandardCharsets.UTF_8));
                JasperReport jasperReportDatail = JasperCompileManager.compileReport(inputDetail);
                JasperPrint jasperPrintDetail = JasperFillManager.fillReport(jasperReportDatail, detailPar, new JREmptyDataSource());

                jasperPrintList.add(jasperPrintDetail);
            }
            if (nonNull(templateMap.get(ContractTemplateType.RECVISIT.name()))) {
                InputStream inputRecv = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RECVISIT.name()).getBytes(StandardCharsets.UTF_8));
                JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
                Map<String, Object> recvPar = new HashMap<>();
                recvPar.put("footerImage", footerImage);
                recvPar.put("clientFullname", "Client Clientovich");
                recvPar.put("clientBirthdate", "Agent Agentovich");
                recvPar.put("clientPassportDealDate", "12.12.2008");
                recvPar.put("clientPassportnumber", "123456");
                recvPar.put("clientPassportserial", "DF1234");
                recvPar.put("clientPassportDealer", "DF1234");
                recvPar.put("clientAddress", "DF1234");
                recvPar.put("clientIIN", "4444333344443333");
                recvPar.put("clientMobilePhone", "7 777 77777 77");
                JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());

                jasperPrintList.add(jasperPrintRecv);
            }
            //----------------------
            Map<String, Object> actPar = new HashMap<>();
            JRBeanCollectionDataSource actDs;
            if (isNull(footerImage)) {
                List<JasperActDto> actItems = new ArrayList<>();

                actItems.add(new JasperActDto("-", "-", "-", "-"));
                actItems.add(new JasperActDto("*", "*", "*", "*"));
                actItems.add(new JasperActDto("#", "#", "#", "#"));
                actDs = new JRBeanCollectionDataSource(actItems);
                actPar.put("CollectionBeanParam", actDs);
                actPar.put("customerIIN", "123123123123");
            } else {
                List<JasperPerspectivaActViewDto> actItems = new ArrayList<>();

                actItems.add(new JasperPerspectivaActViewDto("1", "FIO/Address", "10.10.2019"));
                actItems.add(new JasperPerspectivaActViewDto("2", "FIO/Address", "*"));
                actItems.add(new JasperPerspectivaActViewDto("3", "FIO/Address", "#"));
                actDs = new JRBeanCollectionDataSource(actItems);
                actPar.put("CollectionPerspectivaBuyActView", actDs);
                actPar.put("footerImage", footerImage);
            }
            actPar.put("docNumb", "123456");
            actPar.put("docDate", "12.12.2020");
            actPar.put("agentFullname", userInfoDto.getFullname());

            InputStream inputAct = new ByteArrayInputStream(templateMap.get(ContractTemplateType.ACT_VIEW.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportAct = JasperCompileManager.compileReport(inputAct);
            JasperPrint jasperPrintAct = JasperFillManager.fillReport(jasperReportAct, actPar, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintAct);

            //----------------------
            Map<String, Object> actWorkPar = new HashMap<>();
            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("dirName", "Director Directorovich");
            actWorkPar.put("clientFullname", clientDto.getFullname());
            actWorkPar.put("clientBirthdate", "Agent Agentovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportserial", "DF1234");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", userInfoDto.getFullname());
            actWorkPar.put("clientIIN", "00000000000000");
            actWorkPar.put("footerImage", footerImage);
            InputStream inputActWork = new ByteArrayInputStream(templateMap.get(ContractTemplateType.ACT_WORK.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());

            jasperPrintList.add(jasperPrintActWork);

            return getPages(jasperPrintList);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String generateContractSaleExclusive(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {

        try {

            if (application.getOperationType().isSell()/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/sale/exclusive/main.jrxml");

            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image = getLogo("/jasper/logo.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            mainPar.put("logoImage", image);

            mainPar.put("contractNumber", dto.getContractNumber());
            mainPar.put("contractDate", dto.getContractNumber());
            mainPar.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "TestCity");
            mainPar.put("printDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            mainPar.put("clientFullname", application.getClientLogin());

            mainPar.put("objectFullAddress", "Streat Undefined, Block X, Undefined");
            mainPar.put("objectType", application.getClientLogin());
            mainPar.put("objectPrice", "100 000 000 $");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, mainPar, new JREmptyDataSource());
            //----------------------

            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/sale/exclusive/duties.jrxml");

            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, null, new JREmptyDataSource());
            //----------------------

            //--------------------
            Resource resourcePrice = resourceLoader.getResource("classpath:jasper/sale/exclusive/price.jrxml");

            InputStream inputPrice = resourcePrice.getInputStream();
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, null, new JREmptyDataSource());

            //------------------------

            Resource resourceValid = resourceLoader.getResource("classpath:jasper/sale/exclusive/valid.jrxml");

            InputStream inputValid = resourceValid.getInputStream();
            JasperReport jasperReportValid = JasperCompileManager.compileReport(inputValid);
            JasperPrint jasperPrintValid = JasperFillManager.fillReport(jasperReportValid, null, new JREmptyDataSource());
            //------------------------
            Resource resourceFinal = resourceLoader.getResource("classpath:jasper/sale/exclusive/final.jrxml");

            InputStream inputFinal = resourceFinal.getInputStream();
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal = JasperFillManager.fillReport(jasperReportFinal, null, new JREmptyDataSource());

            //------------------------
            Resource resourceActView = resourceLoader.getResource("classpath:jasper/sale/exclusive/actView.jrxml");

            Map<String, Object> actPar = new HashMap<>();
            List<JasperActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperActViewDto("1", "-", "-", "-", "-s"));
            actItems.add(new JasperActViewDto("2", "*", "*", "*", "*s"));
            actItems.add(new JasperActViewDto("3", "#", "#", "#", "#s"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actPar.put("CollectionBeanParam", actDs);
            actPar.put("docNumb", "123456");
            actPar.put("docDate", "12.12.2020");
            actPar.put("agentFullname", "Agent Agentovich");


            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView = JasperFillManager.fillReport(jasperReportActView, actPar, new JREmptyDataSource());

            //------------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/sale/exclusive/recvisit.jrxml");

            Map<String, Object> recvPar = new HashMap<>();

            recvPar.put("docNumb", "12356");
            recvPar.put("docDate", "123456");
            recvPar.put("actDate", "123123123123");
            recvPar.put("clientFullname", "Client Clientovich");
            recvPar.put("clientBirthdate", "Agent Agentovich");
            recvPar.put("clientPassportDealDate", "12.12.2008");
            recvPar.put("clientPassportnumber", "123456");
            recvPar.put("clientPassportserial", "DF1234");
            recvPar.put("clientPassportDealer", "DF1234");
            recvPar.put("clientAddress", "DF1234");
            recvPar.put("clientIIN", "4444333344443333");
            recvPar.put("clientMobilePhone", "7 777 77777 77");
            recvPar.put("agentFullname", "Agent Agentovich");
            recvPar.put("confidantFullname", "Confidant Confidant");


            InputStream inputRecv = resourceRecv.getInputStream();
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
            //----------------------

            Map<String, Object> propPar = new HashMap<>();

            propPar.put("docNumb", dto.getContractNumber());
            propPar.put("docDate", dto.getContractNumber());
            propPar.put("objectFullAddress", application.getClientLogin());

            propPar.put("objectRCName", "Manhattan");
            propPar.put("objectType", application.getClientLogin());
            propPar.put("objectFloorTotal", "100");

            propPar.put("objectFloor", dto.getContractNumber());
            propPar.put("objectRoomCount", dto.getContractNumber());
            propPar.put("objectArea", "125");
            propPar.put("objectLivingArea", "200");
            propPar.put("objectKitchenArea", "10");
            propPar.put("objectBathroomType", "Раздельный \\Совмещенный");

            propPar.put("objectFurniture", "Streat Undefined, Block X, Undefined");
            propPar.put("objectCollaterial", application.getClientLogin());
            propPar.put("contractSum", "50 000 000");
            propPar.put("objectMaxPrice", "100 000 000");
            //propPar.put("objectMinPrice", "10 000 000");
            propPar.put("objectCadastralNumber", "10 000 000 aaa dddd 44444");
            propPar.put("objectLandArea", "10 000");
            propPar.put("objectDivisible", "10 000 000");
            propPar.put("objectReadyYear", "10 000 000");
            propPar.put("agentFullname", "Agent Agentovich");

            Resource resourceProp = resourceLoader.getResource("classpath:jasper/sale/exclusive/properties.jrxml");
            InputStream inputProp = resourceProp.getInputStream();
            JasperReport jasperReportProp = JasperCompileManager.compileReport(inputProp);
            JasperPrint jasperPrintProp = JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
            //------------------------
            Resource resourceActWork = resourceLoader.getResource("classpath:jasper/sale/exclusive/actWork.jrxml");

            Map<String, Object> actWorkPar = new HashMap<>();

            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("clientFullname", "Client Clientovich");
            actWorkPar.put("clientBirthdate", "Agent Agentovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportserial", "DF1234");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", "Agent Agentovich");


            InputStream inputActWork = resourceActWork.getInputStream();
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());

            //----------------------

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintValid);
            //jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintFinal);
            jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintRecv);
            jasperPrintList.add(jasperPrintProp);
            jasperPrintList.add(jasperPrintActWork);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            //Add the list as a Parameter
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            //this will make a bookmark in the exported PDF for each of the reports
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();


            //byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            String base64String = Base64.encodeBase64String(baos.toByteArray());

            log.info("Done");

            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String generateContractSale(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {

        try {

            if (application.getOperationType().isSell()/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Map<String, String> templateMap = contractForm.getTemplateMap();
            InputStream input = new ByteArrayInputStream(templateMap.get(ContractTemplateType.MAIN.name()).getBytes(StandardCharsets.UTF_8));

            // Add parameters
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image = getLogo(templateMap.get(ContractTemplateType.LOGO.name())); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            mainPar.put("logoImage", image);

            mainPar.put("contractNumber", dto.getContractNumber());
            mainPar.put("contractDate", dto.getContractNumber());
            mainPar.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "TestCity");
            mainPar.put("printDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            mainPar.put("clientFullname", application.getClientLogin());

            mainPar.put("objectFullAddress", "Streat Undefined, Block X, Undefined");
            mainPar.put("objectType", application.getClientLogin());
            mainPar.put("objectPrice", "100 000 000 $");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, mainPar, new JREmptyDataSource());
            //----------------------

            InputStream inputDuties = new ByteArrayInputStream(templateMap.get(ContractTemplateType.DUTIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, null, new JREmptyDataSource());
            //----------------------

            //--------------------
            InputStream inputResp = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RESPONSIBILITIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, null, new JREmptyDataSource());
            //------------------------
            Map<String, Object> recvPar = new HashMap<>();

            recvPar.put("docNumb", "12356");
            recvPar.put("docDate", "123456");
            recvPar.put("actDate", "123123123123");
            recvPar.put("clientFullname", "Client Clientovich");
            recvPar.put("clientBirthdate", "Agent Agentovich");
            recvPar.put("clientPassportDealDate", "12.12.2008");
            recvPar.put("clientPassportnumber", "123456");
            recvPar.put("clientPassportserial", "DF1234");
            recvPar.put("clientPassportDealer", "DF1234");
            recvPar.put("clientAddress", "DF1234");
            recvPar.put("clientIIN", "4444333344443333");
            recvPar.put("clientMobilePhone", "7 777 77777 77");
            recvPar.put("agentFullname", "Agent Agentovich");
            recvPar.put("confidantFullname", "Confidant Confidant");


            InputStream inputRecv = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RECVISIT.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());

            //------------------------

            InputStream inputValid = new ByteArrayInputStream(templateMap.get(ContractTemplateType.VALID.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportValid = JasperCompileManager.compileReport(inputValid);
            JasperPrint jasperPrintValid = JasperFillManager.fillReport(jasperReportValid, null, new JREmptyDataSource());
            //------------------------
            InputStream inputFinal = new ByteArrayInputStream(templateMap.get(ContractTemplateType.FINAL.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal = JasperFillManager.fillReport(jasperReportFinal, null, new JREmptyDataSource());

            //------------------------
            /*Resource resourceActView = resourceLoader.getResource("classpath:jasper/standart/standart/actView.jrxml");

            Map<String, Object> actPar = new HashMap<>();
            List<JasperActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperActViewStandartDto("1","-", "-", "-", "-s", "#1"));
            actItems.add(new JasperActViewStandartDto("2","-", "-", "-", "-s", "#2"));
            actItems.add(new JasperActViewStandartDto("2","-", "-", "-", "-s", "#3"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actPar.put("CollectionBeanParam", actDs);
            actPar.put("docNumb", "123456");
            actPar.put("docDate", "12.12.2020");
            actPar.put("agentFullname", "Agent Agentovich");


            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView= JasperFillManager.fillReport(jasperReportActView, actPar, new JREmptyDataSource());*/
            //----------------------

            Map<String, Object> propPar = new HashMap<>();

            propPar.put("docNumb", dto.getContractNumber());
            propPar.put("docDate", dto.getContractNumber());
            propPar.put("objectFullAddress", application.getClientLogin());

            propPar.put("objectRCName", "Manhattan");
            propPar.put("objectType", application.getClientLogin());
            propPar.put("objectFloorTotal", "100");

            propPar.put("objectFloor", dto.getContractNumber());
            propPar.put("objectRoomCount", dto.getContractNumber());
            propPar.put("objectArea", "125");
            propPar.put("objectLivingArea", "200");
            propPar.put("objectKitchenArea", "10");
            propPar.put("objectBathroomType", "Раздельный \\Совмещенный");

            propPar.put("objectFurniture", "Streat Undefined, Block X, Undefined");
            propPar.put("objectCollaterial", application.getClientLogin());
            propPar.put("contractSum", "50 000 000");
            propPar.put("objectMaxPrice", "100 000 000");
            //propPar.put("objectMinPrice", "10 000 000");
            propPar.put("objectCadastralNumber", "10 000 000 aaa dddd 44444");
            propPar.put("objectLandArea", "10 000");
            propPar.put("objectDivisible", "10 000 000");
            propPar.put("objectReadyYear", "10 000 000");
            propPar.put("agentFullname", "Agent Agentovich");

            InputStream inputProp = new ByteArrayInputStream(templateMap.get(ContractTemplateType.PROPERTIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportProp = JasperCompileManager.compileReport(inputProp);
            JasperPrint jasperPrintProp = JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
            //------------------------
            Map<String, Object> actWorkPar = new HashMap<>();

            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("clientFullname", "Client Clientovich");
            actWorkPar.put("clientBirthdate", "Agent Agentovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportserial", "DF1234");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", "Agent Agentovich");


            InputStream inputActWork = new ByteArrayInputStream(templateMap.get(ContractTemplateType.ACT_WORK.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());

            //----------------------

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintResp);

            //jasperPrintList.add(jasperPrintValid);
            //jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintRecv);
            //jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintFinal);

            jasperPrintList.add(jasperPrintProp);
            jasperPrintList.add(jasperPrintActWork);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            //Add the list as a Parameter
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            //this will make a bookmark in the exported PDF for each of the reports
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();


            //byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            String base64String = Base64.encodeBase64String(baos.toByteArray());

            log.info("Done");

            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String generateContractBuyPerspective(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {
        try {
            if (application.getOperationType().isSell() || isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Map<String, String> templateMap = contractForm.getTemplateMap();
            InputStream input = new ByteArrayInputStream(templateMap.get(ContractTemplateType.MAIN.name()).getBytes(StandardCharsets.UTF_8));

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            InputStream logoImage = getLogo(templateMap.get(ContractTemplateType.LOGO.name())); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            parameters.put("logoImage", logoImage);

            InputStream footerImage = getLogo(templateMap.get(ContractTemplateType.FOOTER_LOGO.name()));
            parameters.put("footerImage", footerImage);
            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", sdfDate.format(new Date()));
            parameters.put("cityKZ", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("cityRU", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", sdfDate.format(new Date()));
            parameters.put("clientFullname", "Client Undefined");

            parameters.put("objectRegion", nonNull(district) ? district.getMultiLang().getNameRu() : "");
            parameters.put("objectType", application.getObjectType().getMultiLang().getNameRu());
            parameters.put("objectRoomCount", nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
            parameters.put("objectArea", nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
            parameters.put("objectFloor", nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, parameters, new JREmptyDataSource());
            //----------------------
            InputStream inputDuties = new ByteArrayInputStream(templateMap.get(ContractTemplateType.DUTIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            Map<String, Object> dutiesPar = new HashMap<>();
            dutiesPar.put("footerImage", footerImage);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());
            //----------------------
            InputStream inputPrice = new ByteArrayInputStream(templateMap.get(ContractTemplateType.PRICE.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            Map<String, Object> pricePar = new HashMap<>();
            pricePar.put("footerImage", footerImage);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, pricePar, new JREmptyDataSource());
            //----------------------
            InputStream inputResp = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RESPONSIBILITIES.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            Map<String, Object> respPar = new HashMap<>();
            respPar.put("footerImage", footerImage);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, respPar, new JREmptyDataSource());
            //----------------------
            InputStream inputRecv = new ByteArrayInputStream(templateMap.get(ContractTemplateType.RECVISIT.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            Map<String, Object> recvPar = new HashMap<>();
            recvPar.put("footerImage", footerImage);
            recvPar.put("clientFullname", "Client Clientovich");
            recvPar.put("clientBirthdate", "Agent Agentovich");
            recvPar.put("clientPassportDealDate", "12.12.2008");
            recvPar.put("clientPassportnumber", "123456");
            recvPar.put("clientPassportserial", "DF1234");
            recvPar.put("clientPassportDealer", "DF1234");
            recvPar.put("clientAddress", "DF1234");
            recvPar.put("clientIIN", "4444333344443333");
            recvPar.put("clientMobilePhone", "7 777 77777 77");
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
            //------------------------
            Map<String, Object> actViewPar = new HashMap<>();
            List<JasperPerspectivaActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperPerspectivaActViewDto("1", "FIO/Address", "10.10.2019"));
            actItems.add(new JasperPerspectivaActViewDto("2", "FIO/Address", "*"));
            actItems.add(new JasperPerspectivaActViewDto("3", "FIO/Address", "#"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actViewPar.put("CollectionPerspectivaBuyActView", actDs);
            actViewPar.put("docNumb", "123456");
            actViewPar.put("docDate", "12.12.2020");
            actViewPar.put("agentFullname", "Agent Agentovich");
            actViewPar.put("footerImage", footerImage);

            InputStream inputActView = new ByteArrayInputStream(templateMap.get(ContractTemplateType.ACT_VIEW.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView = JasperFillManager.fillReport(jasperReportActView, actViewPar, new JREmptyDataSource());

            //---------------------
            Map<String, Object> actWorkPar = new HashMap<>();
            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("dirName", "Director Directorovich");
            actWorkPar.put("clientFullname", "Client Clientovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", "Agent Agentovich");
            actWorkPar.put("clientIIN", "00000000000000");
            actViewPar.put("footerImage", footerImage);
            InputStream inputActWork = new ByteArrayInputStream(templateMap.get(ContractTemplateType.ACT_WORK.name()).getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());
            //---------------------
            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintRecv);
            jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintActWork);

            return getPages(jasperPrintList);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private InputStream getLogo(String path) {
        return getClass().getResourceAsStream(path);
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

    private String generateContractSaleExclusivePerspective(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {
        try {

            if (isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Resource resource = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/main.jrxml");
            Map<String, String> templateMap = contractForm.getTemplateMap();

            InputStream input = new ByteArrayInputStream(templateMap.get(ContractTemplateType.MAIN.name()).getBytes(StandardCharsets.UTF_8));
            InputStream footerImage = getFooterImagePerspective();
            InputStream logoImage = getLogoPerspective();

            Map<String, Object> parameters = new HashMap<>();


            parameters.put("logoImage", logoImage);
            parameters.put("footerImage", footerImage);
            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", sdfDate.format(new Date()));
            parameters.put("cityKZ", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("cityRU", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", sdfDate.format(new Date()));
            parameters.put("clientFullname", "Client Undefined");

            parameters.put("objectRegion", nonNull(district) ? district.getMultiLang().getNameRu() : "");
            parameters.put("objectType", application.getObjectType().getMultiLang().getNameRu());
            parameters.put("objectRoomCount", nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
            parameters.put("objectArea", nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
            parameters.put("objectFloor", nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, parameters, new JREmptyDataSource());
            //-----------------------
            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/duties.jrxml");

            Map<String, Object> dutiesPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            dutiesPar.put("footerImage", footerImage);
            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());
            //----------------------

            Resource resourcePrice = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/price.jrxml");
            InputStream inputPrice = resourcePrice.getInputStream();
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            Map<String, Object> pricePar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            pricePar.put("footerImage", footerImage);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, pricePar, new JREmptyDataSource());
            //----------------------
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/responsibilities.jrxml");
            Map<String, Object> respPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            respPar.put("footerImage", footerImage);
            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, respPar, new JREmptyDataSource());


            //----------------------
            Resource resourceFinal = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/final.jrxml");

            Map<String, Object> finalPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            finalPar.put("footerImage", footerImage);
            InputStream inputFinal = resourceFinal.getInputStream();
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal = JasperFillManager.fillReport(jasperReportFinal, finalPar, new JREmptyDataSource());

            //------------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/recvisit.jrxml");

            Map<String, Object> recvPar = new HashMap<>();

            footerImage = getFooterImagePerspective();
            recvPar.put("footerImage", footerImage);
            recvPar.put("docNumb", "12356");
            recvPar.put("docDate", "123456");
            recvPar.put("actDate", "123123123123");
            recvPar.put("clientFullname", "Client Clientovich");
            recvPar.put("clientBirthdate", "Agent Agentovich");
            recvPar.put("clientPassportDealDate", "12.12.2008");
            recvPar.put("clientPassportnumber", "123456");
            recvPar.put("clientPassportserial", "DF1234");
            recvPar.put("clientPassportDealer", "DF1234");
            recvPar.put("clientAddress", "DF1234");
            recvPar.put("clientIIN", "4444333344443333");
            recvPar.put("clientMobilePhone", "7 777 77777 77");
            recvPar.put("agentFullname", "Agent Agentovich");
            recvPar.put("confidantFullname", "Confidant Confidant");


            InputStream inputRecv = resourceRecv.getInputStream();
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
            //------------------------
            Resource resourceActView = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/actView.jrxml");

            Map<String, Object> actViewPar = new HashMap<>();
            List<JasperPerspectivaActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperPerspectivaActViewDto("1", "FIO/Address", "10.10.2019"));
            actItems.add(new JasperPerspectivaActViewDto("2", "FIO/Address", "*"));
            actItems.add(new JasperPerspectivaActViewDto("3", "FIO/Address", "#"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actViewPar.put("CollectionPerspectivaBuyActView", actDs);
            actViewPar.put("docNumb", "123456");
            actViewPar.put("docDate", "12.12.2020");
            actViewPar.put("agentFullname", "Agent Agentovich");
            footerImage = getFooterImagePerspective();
            actViewPar.put("footerImage", footerImage);

            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView = JasperFillManager.fillReport(jasperReportActView, actViewPar, new JREmptyDataSource());
            //----------------------
            Map<String, Object> propPar = new HashMap<>();

            propPar.put("docNumb", dto.getContractNumber());
            propPar.put("docDate", dto.getContractNumber());
            propPar.put("objectFullAddress", application.getClientLogin());

            propPar.put("objectRCName", "Manhattan");
            propPar.put("objectType", application.getClientLogin());
            propPar.put("objectFloorTotal", "100");

            propPar.put("objectFloor", dto.getContractNumber());
            propPar.put("objectRoomCount", dto.getContractNumber());
            propPar.put("objectArea", "125");
            propPar.put("objectLivingArea", "200");
            propPar.put("objectKitchenArea", "10");
            propPar.put("objectBathroomTypeRU", "Раздельный \\Совмещенный");
            propPar.put("objectBathroomTypeKZ", "қазақша");

            propPar.put("objectFurnitureRU", "list list");
            propPar.put("objectFurnitureKZ", "list kz list kz");

            propPar.put("objectCollaterial", application.getClientLogin());
            propPar.put("contractSum", "50 000 000");
            propPar.put("objectMaxPrice", "100 000 000");
            //propPar.put("objectMinPrice", "10 000 000");
            propPar.put("objectCadastralNumber", "10 000 000 aaa dddd 44444");
            propPar.put("objectLandArea", "10 000");
            propPar.put("objectDivisibleRU", "ДА");
            propPar.put("objectDivisibleKZ", "ИӘ");
            propPar.put("objectReadyYear", "10 000 000");
            propPar.put("agentFullname", "Agent Agentovich");
            footerImage = getFooterImagePerspective();
            propPar.put("footerImage", footerImage);

            Resource resourceProp = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/properties.jrxml");
            InputStream inputProp = resourceProp.getInputStream();
            JasperReport jasperReportProp = JasperCompileManager.compileReport(inputProp);
            JasperPrint jasperPrintProp = JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
            //---------------------
            Resource resourceActWork = resourceLoader.getResource("classpath:jasper/perspectiva/sale/exclusive/actWork.jrxml");
            Map<String, Object> actWorkPar = new HashMap<>();
            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("dirName", "Director Directorovich");
            actWorkPar.put("clientFullname", "Client Clientovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", "Agent Agentovich");
            actWorkPar.put("clientIIN", "00000000000000");
            footerImage = getFooterImagePerspective();
            actViewPar.put("footerImage", footerImage);
            InputStream inputActWork = resourceActWork.getInputStream();
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());
            //------------------------

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintFinal);
            jasperPrintList.add(jasperPrintRecv);
            jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintProp);
            jasperPrintList.add(jasperPrintActWork);


            return getPages(jasperPrintList);
            //log.info("Perspective Sale Done");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String generateContractSaleStandartPerspective(Application application, ContractFormDto dto, ProfileClientDto clientDto, UserInfoDto userInfoDto, ContractFormTemplateDto contractForm) {
        try {
            if (isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Resource resource = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/main.jrxml");
            List<String> userLogin = new ArrayList<>();
            userLogin.add(application.getClientLogin());

            InputStream input = resource.getInputStream();
            InputStream footerImage = getFooterImagePerspective();
            InputStream logoImage = getLogoPerspective();

            Map<String, Object> parameters = new HashMap<>();


            parameters.put("logoImage", logoImage);
            parameters.put("footerImage", footerImage);
            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", sdfDate.format(new Date()));
            parameters.put("cityKZ", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("cityRU", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", sdfDate.format(new Date()));
            parameters.put("clientFullname", "Client Undefined");

            parameters.put("objectRegion", nonNull(district) ? district.getMultiLang().getNameRu() : "");
            parameters.put("objectType", application.getObjectType().getMultiLang().getNameRu());
            parameters.put("objectRoomCount", nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
            parameters.put("objectArea", nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
            parameters.put("objectFloor", nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, parameters, new JREmptyDataSource());
            //-----------------------
            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/duties.jrxml");

            Map<String, Object> dutiesPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            dutiesPar.put("footerImage", footerImage);
            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());
            //----------------------

            Resource resourcePrice = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/price.jrxml");
            InputStream inputPrice = resourcePrice.getInputStream();
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            Map<String, Object> pricePar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            pricePar.put("footerImage", footerImage);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, pricePar, new JREmptyDataSource());
            //----------------------
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/responsibilities.jrxml");
            Map<String, Object> respPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            respPar.put("footerImage", footerImage);
            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, respPar, new JREmptyDataSource());


            //----------------------
            Resource resourceFinal = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/final.jrxml");

            Map<String, Object> finalPar = new HashMap<>();
            footerImage = getFooterImagePerspective();
            finalPar.put("footerImage", footerImage);
            InputStream inputFinal = resourceFinal.getInputStream();
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal = JasperFillManager.fillReport(jasperReportFinal, finalPar, new JREmptyDataSource());

            //------------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/recvisit.jrxml");

            Map<String, Object> recvPar = new HashMap<>();

            footerImage = getFooterImagePerspective();
            recvPar.put("footerImage", footerImage);
            recvPar.put("docNumb", "12356");
            recvPar.put("docDate", "123456");
            recvPar.put("actDate", "123123123123");
            recvPar.put("clientFullname", "Client Clientovich");
            recvPar.put("clientBirthdate", "Agent Agentovich");
            recvPar.put("clientPassportDealDate", "12.12.2008");
            recvPar.put("clientPassportnumber", "123456");
            recvPar.put("clientPassportserial", "DF1234");
            recvPar.put("clientPassportDealer", "DF1234");
            recvPar.put("clientAddress", "DF1234");
            recvPar.put("clientIIN", "4444333344443333");
            recvPar.put("clientMobilePhone", "7 777 77777 77");
            recvPar.put("agentFullname", "Agent Agentovich");
            recvPar.put("confidantFullname", "Confidant Confidant");


            InputStream inputRecv = resourceRecv.getInputStream();
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            JasperPrint jasperPrintRecv = JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
            //------------------------
            Resource resourceActView = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/actView.jrxml");

            Map<String, Object> actViewPar = new HashMap<>();
            List<JasperPerspectivaActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperPerspectivaActViewDto("1", "FIO/Address", "10.10.2019"));
            actItems.add(new JasperPerspectivaActViewDto("2", "FIO/Address", "*"));
            actItems.add(new JasperPerspectivaActViewDto("3", "FIO/Address", "#"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actViewPar.put("CollectionPerspectivaBuyActView", actDs);
            actViewPar.put("docNumb", "123456");
            actViewPar.put("docDate", "12.12.2020");
            actViewPar.put("agentFullname", "Agent Agentovich");
            footerImage = getFooterImagePerspective();
            actViewPar.put("footerImage", footerImage);

            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView = JasperFillManager.fillReport(jasperReportActView, actViewPar, new JREmptyDataSource());
            //----------------------
            Map<String, Object> propPar = new HashMap<>();

            propPar.put("docNumb", dto.getContractNumber());
            propPar.put("docDate", dto.getContractNumber());
            propPar.put("objectFullAddress", application.getClientLogin());

            propPar.put("objectRCName", "Manhattan");
            propPar.put("objectType", application.getClientLogin());
            propPar.put("objectFloorTotal", "100");

            propPar.put("objectFloor", dto.getContractNumber());
            propPar.put("objectRoomCount", dto.getContractNumber());
            propPar.put("objectArea", "125");
            propPar.put("objectLivingArea", "200");
            propPar.put("objectKitchenArea", "10");
            propPar.put("objectBathroomTypeRU", "Раздельный \\Совмещенный");
            propPar.put("objectBathroomTypeKZ", "қазақша");

            propPar.put("objectFurnitureRU", "list list");
            propPar.put("objectFurnitureKZ", "list kz list kz");

            propPar.put("objectCollaterial", application.getClientLogin());
            propPar.put("contractSum", "50 000 000");
            propPar.put("objectMaxPrice", "100 000 000");
            //propPar.put("objectMinPrice", "10 000 000");
            propPar.put("objectCadastralNumber", "10 000 000 aaa dddd 44444");
            propPar.put("objectLandArea", "10 000");
            propPar.put("objectDivisibleRU", "ДА");
            propPar.put("objectDivisibleKZ", "ИӘ");
            propPar.put("objectReadyYear", "10 000 000");
            propPar.put("agentFullname", "Agent Agentovich");
            footerImage = getFooterImagePerspective();
            propPar.put("footerImage", footerImage);

            Resource resourceProp = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/properties.jrxml");
            InputStream inputProp = resourceProp.getInputStream();
            JasperReport jasperReportProp = JasperCompileManager.compileReport(inputProp);
            JasperPrint jasperPrintProp = JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
            //---------------------
            Resource resourceActWork = resourceLoader.getResource("classpath:jasper/perspectiva/sale/standart/actWork.jrxml");
            Map<String, Object> actWorkPar = new HashMap<>();
            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("dirName", "Director Directorovich");
            actWorkPar.put("clientFullname", "Client Clientovich");
            actWorkPar.put("clientPassportDealDate", "12.12.2008");
            actWorkPar.put("clientPassportnumber", "123456");
            actWorkPar.put("clientPassportDealer", "DF1234");
            actWorkPar.put("clientAddress", "DF1234");
            actWorkPar.put("agentFullname", "Agent Agentovich");
            actWorkPar.put("clientIIN", "00000000000000");
            footerImage = getFooterImagePerspective();
            actViewPar.put("footerImage", footerImage);
            InputStream inputActWork = resourceActWork.getInputStream();
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork = JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());
            //------------------------

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintFinal);
            jasperPrintList.add(jasperPrintRecv);
            jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintProp);
            jasperPrintList.add(jasperPrintActWork);


            return getPages(jasperPrintList);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
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
