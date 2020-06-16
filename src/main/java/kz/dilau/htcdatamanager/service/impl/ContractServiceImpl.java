package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.ContractStatus;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ContractStatusRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import kz.dilau.htcdatamanager.web.dto.ProfileClientDto;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
    private final ContractStatusRepository contractStatusRepository;

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
        String authorName = getAuthorName();
        if (nonNull(authorName) && (authorName.equalsIgnoreCase(application.getCreatedBy()) ||
                authorName.equalsIgnoreCase(application.getCurrentAgent())) && nonNull(application.getContract())) {
            return new ContractFormDto(application.getContract());
        } else {
            return null;
        }
    }

    @Override
    public String generateContract(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        String result;
        if (dto.getIsExclusive()) {
            result = generateContractSaleExclusive(application, dto);
        } else if (application.getOperationType().isBuy()) {
            result = generateContractBuy(application, dto);
        } else {
            result = generateContractSale(application, dto);
        }

        if (dto.getGuid().equals("perspective_buy")) {
            result = generateContractBuyPerspective(application, dto);
        }

        if (nonNull(result)) {
            //saveContract(dto, application, entityService.mapEntity(ContractStatus.class, ContractStatus.GENERATED));
            return result;
        } else {
            return null;
        }
    }


    private String generateContractBuy(Application application, ContractFormDto dto) {
        try {

            if (application.getOperationType().isSell() || isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Resource resource = resourceLoader.getResource("classpath:jasper/buy/main.jrxml");
            List<String> userLogin = new ArrayList<>();
            userLogin.add(application.getClientLogin());
            List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(userLogin);
            if (profileClientDtoList.isEmpty()) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ProfileClientDto clientDto = profileClientDtoList.get(0);
            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            InputStream image = getClass().getResourceAsStream("/jasper/logo.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            parameters.put("logoImage", image);

            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", sdfDate.format(new Date()));
            parameters.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", sdfDate.format(new Date()));
            parameters.put("clientFullname", clientDto.getFullname());

            parameters.put("objectRegion", nonNull(district) ? district.getMultiLang().getNameRu() : "");
            parameters.put("objectType", application.getObjectType().getMultiLang().getNameRu());
            parameters.put("objectRoomCount", nonNull(purchaseInfo) ? purchaseInfo.getNumberOfRoomsFrom() + " - " + purchaseInfo.getNumberOfRoomsTo() : "");
            parameters.put("objectArea", nonNull(purchaseInfo) ? purchaseInfo.getTotalAreaFrom() + " - " + purchaseInfo.getTotalAreaTo() : "");
            parameters.put("objectFloor", nonNull(purchaseInfo) ? purchaseInfo.getFloorFrom() + " - " + purchaseInfo.getFloorTo() : "");

            JasperReport jasperReportBasic = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrintBasic = JasperFillManager.fillReport(jasperReportBasic, parameters, new JREmptyDataSource());
            //----------------------

            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/buy/duties.jrxml");

            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, null, new JREmptyDataSource());


            //--------------------
            Resource resourcePrice = resourceLoader.getResource("classpath:jasper/buy/price.jrxml");

            InputStream inputPrice = resourcePrice.getInputStream();
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, null, new JREmptyDataSource());

            //----------------------
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/buy/responsibilities.jrxml");
            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, null, new JREmptyDataSource());

            //----------------------
            Resource resourceDetail = resourceLoader.getResource("classpath:jasper/buy/detail.jrxml");

            Map<String, Object> detailPar = new HashMap<>();
            List<JasperBasicDto> detailItems = new ArrayList<>();

            detailItems.add(new JasperBasicDto("-", "-"));
            JRBeanCollectionDataSource detailDs = new JRBeanCollectionDataSource(detailItems);
            detailPar.put("CollectionBeanParam", detailDs);


            InputStream inputDetail = resourceDetail.getInputStream();
            JasperReport jasperReportDatail = JasperCompileManager.compileReport(inputDetail);
            JasperPrint jasperPrintDetail = JasperFillManager.fillReport(jasperReportDatail, detailPar, new JREmptyDataSource());
            //----------------------
            Resource resourceAct = resourceLoader.getResource("classpath:jasper/buy/act.jrxml");

            Map<String, Object> actPar = new HashMap<>();
            List<JasperActDto> actItems = new ArrayList<>();

            actItems.add(new JasperActDto("-", "-", "-", "-"));
            actItems.add(new JasperActDto("*", "*", "*", "*"));
            actItems.add(new JasperActDto("#", "#", "#", "#"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actPar.put("CollectionBeanParam", actDs);
            actPar.put("docNumb", "123456");
            actPar.put("customerIIN", "123123123123");
            actPar.put("docDate", "12.12.2020");
            actPar.put("agentFullname", "Agent Agentovich");


            InputStream inputAct = resourceAct.getInputStream();
            JasperReport jasperReportAct = JasperCompileManager.compileReport(inputAct);
            JasperPrint jasperPrintAct = JasperFillManager.fillReport(jasperReportAct, actPar, new JREmptyDataSource());
            //----------------------
            Resource resourceActWork = resourceLoader.getResource("classpath:jasper/buy/actWork.jrxml");
            Map<String, Object> actWorkPar = new HashMap<>();
            actWorkPar.put("docNumb", "12356");
            actWorkPar.put("docDate", "123456");
            actWorkPar.put("actDate", "123123123123");
            actWorkPar.put("dirName", "Director Directorovich");
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
            JasperPrint jasperPrintActWork= JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());
            //--------------------------

            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintDetail);
            jasperPrintList.add(jasperPrintAct);
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

    public String generateContractSaleExclusive(Application application, ContractFormDto dto) {

        try {

            if (application.getOperationType().isSell()/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/sale/exclusive/main.jrxml");

            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image = getClass().getResourceAsStream("/jasper/logo.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
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
            JasperPrint jasperPrintValid= JasperFillManager.fillReport(jasperReportValid, null, new JREmptyDataSource());
            //------------------------
            Resource resourceFinal = resourceLoader.getResource("classpath:jasper/sale/exclusive/final.jrxml");

            InputStream inputFinal = resourceFinal.getInputStream();
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal= JasperFillManager.fillReport(jasperReportFinal, null, new JREmptyDataSource());

            //------------------------
            Resource resourceActView = resourceLoader.getResource("classpath:jasper/sale/exclusive/actView.jrxml");

            Map<String, Object> actPar = new HashMap<>();
            List<JasperActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperActViewDto("1","-", "-", "-", "-s"));
            actItems.add(new JasperActViewDto("2","*", "*", "*", "*s"));
            actItems.add(new JasperActViewDto("3","#", "#", "#", "#s"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actPar.put("CollectionBeanParam", actDs);
            actPar.put("docNumb", "123456");
            actPar.put("docDate", "12.12.2020");
            actPar.put("agentFullname", "Agent Agentovich");


            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView= JasperFillManager.fillReport(jasperReportActView, actPar, new JREmptyDataSource());

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
            JasperPrint jasperPrintRecv= JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());
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
            JasperPrint jasperPrintProp= JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
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
            JasperPrint jasperPrintActWork= JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());

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

            saveContract(dto, application, contractStatusRepository.getOne(ContractStatus.GENERATED));
            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String generateContractSale(Application application, ContractFormDto dto) {

        try {

            if (application.getOperationType().isSell()/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/sale/standart/main.jrxml");

            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> mainPar = new HashMap<>();
            InputStream image  = getClass().getResourceAsStream("/jasper/logo.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
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

            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/sale/standart/duties.jrxml");

            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, null, new JREmptyDataSource());
            //----------------------

            //--------------------
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/sale/standart/responsibilities.jrxml");

            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, null, new JREmptyDataSource());
            //------------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/sale/standart/recvisit.jrxml");

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
            JasperPrint jasperPrintRecv= JasperFillManager.fillReport(jasperReportRecv, recvPar, new JREmptyDataSource());

            //------------------------

            Resource resourceValid = resourceLoader.getResource("classpath:jasper/sale/exclusive/valid.jrxml");

            InputStream inputValid = resourceValid.getInputStream();
            JasperReport jasperReportValid = JasperCompileManager.compileReport(inputValid);
            JasperPrint jasperPrintValid= JasperFillManager.fillReport(jasperReportValid, null, new JREmptyDataSource());
            //------------------------
            Resource resourceFinal = resourceLoader.getResource("classpath:jasper/sale/standart/final.jrxml");

            InputStream inputFinal = resourceFinal.getInputStream();
            JasperReport jasperReportFinal = JasperCompileManager.compileReport(inputFinal);
            JasperPrint jasperPrintFinal= JasperFillManager.fillReport(jasperReportFinal, null, new JREmptyDataSource());

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

            Resource resourceProp = resourceLoader.getResource("classpath:jasper/sale/exclusive/properties.jrxml");
            InputStream inputProp = resourceProp.getInputStream();
            JasperReport jasperReportProp = JasperCompileManager.compileReport(inputProp);
            JasperPrint jasperPrintProp= JasperFillManager.fillReport(jasperReportProp, propPar, new JREmptyDataSource());
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
            JasperPrint jasperPrintActWork= JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());

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

    private String generateContractBuyPerspective(Application application, ContractFormDto dto) {
        try {

            /*if (application.getOperationType().isSell() || isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }*/
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            PurchaseInfo purchaseInfo = purchaseData.getPurchaseInfo();
            City city = purchaseData.getCity();
            District district = purchaseData.getDistrict();
            Resource resource = resourceLoader.getResource("classpath:jasper/perspectiva/buy/main.jrxml");
            List<String> userLogin = new ArrayList<>();
            userLogin.add(application.getClientLogin());

            //тут 404 ошибку бросает у меня
            /*List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(userLogin);
            if (profileClientDtoList.isEmpty()) {
                throw BadRequestException.createTemplateException("error.application.contract");
            }
            ProfileClientDto clientDto = profileClientDtoList.get(0);*/
            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            InputStream logoImage = getClass().getResourceAsStream("/jasper/logo_perspectiva.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            parameters.put("logoImage", logoImage);

            InputStream footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
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
            Resource resourceDuties = resourceLoader.getResource("classpath:jasper/perspectiva/buy/duties.jrxml");
            InputStream inputDuties = resourceDuties.getInputStream();
            JasperReport jasperReportDuties = JasperCompileManager.compileReport(inputDuties);
            Map<String, Object> dutiesPar = new HashMap<>();
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
            dutiesPar.put("footerImage", footerImage);
            JasperPrint jasperPrintDuties = JasperFillManager.fillReport(jasperReportDuties, dutiesPar, new JREmptyDataSource());
            //----------------------
            Resource resourcePrice = resourceLoader.getResource("classpath:jasper/perspectiva/buy/price.jrxml");
            InputStream inputPrice = resourcePrice.getInputStream();
            JasperReport jasperReportPrice = JasperCompileManager.compileReport(inputPrice);
            Map<String, Object> pricePar = new HashMap<>();
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
            pricePar.put("footerImage", footerImage);
            JasperPrint jasperPrintPrice = JasperFillManager.fillReport(jasperReportPrice, pricePar, new JREmptyDataSource());
            //----------------------
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/perspectiva/buy/responsibility.jrxml");
            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            Map<String, Object> respPar = new HashMap<>();
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
            respPar.put("footerImage", footerImage);
            JasperPrint jasperPrintResp = JasperFillManager.fillReport(jasperReportResp, respPar, new JREmptyDataSource());
            //----------------------
            Resource resourceRecv = resourceLoader.getResource("classpath:jasper/perspectiva/buy/recvisit.jrxml");
            InputStream inputRecv = resourceRecv.getInputStream();
            JasperReport jasperReportRecv = JasperCompileManager.compileReport(inputRecv);
            Map<String, Object> recvPar = new HashMap<>();
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
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
            Resource resourceActView = resourceLoader.getResource("classpath:jasper/perspectiva/buy/actView.jrxml");

            Map<String, Object> actViewPar = new HashMap<>();
            List<JasperPerspectivaActViewDto> actItems = new ArrayList<>();

            actItems.add(new JasperPerspectivaActViewDto("1","FIO/Address", "10.10.2019"));
            actItems.add(new JasperPerspectivaActViewDto("2","FIO/Address", "*"));
            actItems.add(new JasperPerspectivaActViewDto("3","FIO/Address", "#"));
            JRBeanCollectionDataSource actDs = new JRBeanCollectionDataSource(actItems);
            actViewPar.put("CollectionPerspectivaBuyActView", actDs);
            actViewPar.put("docNumb", "123456");
            actViewPar.put("docDate", "12.12.2020");
            actViewPar.put("agentFullname", "Agent Agentovich");
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
            actViewPar.put("footerImage", footerImage);

            InputStream inputActView = resourceActView.getInputStream();
            JasperReport jasperReportActView = JasperCompileManager.compileReport(inputActView);
            JasperPrint jasperPrintActView= JasperFillManager.fillReport(jasperReportActView, actViewPar, new JREmptyDataSource());

            //---------------------
            Resource resourceActWork = resourceLoader.getResource("classpath:jasper/perspectiva/buy/actWork.jrxml");
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
            footerImage = getClass().getResourceAsStream("/jasper/logo_footer_perspectiva.png");
            actViewPar.put("footerImage", footerImage);
            InputStream inputActWork = resourceActWork.getInputStream();
            JasperReport jasperReportActWork = JasperCompileManager.compileReport(inputActWork);
            JasperPrint jasperPrintActWork= JasperFillManager.fillReport(jasperReportActWork, actWorkPar, new JREmptyDataSource());
            //---------------------
            List<JasperPrint> jasperPrintList = new ArrayList<>();
            jasperPrintList.add(jasperPrintBasic);
            jasperPrintList.add(jasperPrintDuties);
            jasperPrintList.add(jasperPrintPrice);
            jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintRecv);
            jasperPrintList.add(jasperPrintActView);
            jasperPrintList.add(jasperPrintActWork);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            //Add the list as a Parameter
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            //this will make a bookmark in the exported PDF for each of the reports
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();


            String base64String = Base64.encodeBase64String(baos.toByteArray());
            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public Long missContract(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationContract contract = saveContract(dto, application, entityService.mapEntity(ContractStatus.class, ContractStatus.MISSING));
        return contract.getId();
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
