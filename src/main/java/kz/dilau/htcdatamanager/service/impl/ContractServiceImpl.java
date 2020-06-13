package kz.dilau.htcdatamanager.service.impl;

import com.itextpdf.text.ListItem;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.ApplicationContract;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.ContractStatus;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ContractStatusRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperActViewDto;
import kz.dilau.htcdatamanager.web.dto.jasper.JasperBasicDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private final ApplicationContractRepository contractRepository;
    private final ApplicationService applicationService;
    private final ResourceLoader resourceLoader;
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

        if (dto.getGuid().equals("buy")) {
            return generateContractBuy(application,dto);
        }

        if (dto.getGuid().equals("sale")) {
            return generateContractSale(application, dto);
        }

        if (dto.getGuid().equals("sale_exclusive")) {
            return generateContractSaleExclusive(application,dto);
        }
        return "empty";
    }


    public String generateContractBuy(Application application, ContractFormDto dto) {

        try {

            if (application.getOperationType().getCode().equals(OperationType.SELL)/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/buy/main.jrxml");

            InputStream input = resource.getInputStream();

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            InputStream image  = getClass().getResourceAsStream("/jasper/logo.png"); //ImageIO.read(getClass().getResource("/images/IMAGE.png"));
            parameters.put("logoImage", image);

            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("contractDate", dto.getContractNumber());
            parameters.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "TestCity");
            parameters.put("printDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            parameters.put("clientFullname", application.getClientLogin());

            parameters.put("objectRegion", application.getClientLogin());
            parameters.put("objectType", application.getClientLogin());
            parameters.put("objectRoomCount", application.getClientLogin());
            parameters.put("objectArea", application.getClientLogin());
            parameters.put("objectFloor", application.getClientLogin());

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
            Resource resourceResp = resourceLoader.getResource("classpath:jasper/buy/duties.jrxml");
            InputStream inputResp = resourceResp.getInputStream();
            JasperReport jasperReportResp = JasperCompileManager.compileReport(inputResp);
            JasperPrint jasperPrintResp= JasperFillManager.fillReport(jasperReportResp, null, new JREmptyDataSource());

            //----------------------
            Resource resourceDetail = resourceLoader.getResource("classpath:jasper/buy/detail.jrxml");

            Map<String, Object> detailPar = new HashMap<>();
            List<JasperBasicDto> detailItems = new ArrayList<>();

            detailItems.add(new JasperBasicDto("-", "-"));
            JRBeanCollectionDataSource detailDs = new JRBeanCollectionDataSource(detailItems);
            detailPar.put("CollectionBeanParam", detailDs);


            InputStream inputDetail = resourceDetail.getInputStream();
            JasperReport jasperReportDatail = JasperCompileManager.compileReport(inputDetail);
            JasperPrint jasperPrintDetail= JasperFillManager.fillReport(jasperReportDatail, detailPar, new JREmptyDataSource());
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
            JasperPrint jasperPrintAct= JasperFillManager.fillReport(jasperReportAct, actPar, new JREmptyDataSource());
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

            if (application.getOperationType().getCode().equals(OperationType.SELL)/* || isNull(application.getApplicationPurchaseData())*/) {
                //throw BadRequestException.idMustNotBeNull();
            }
            City city = null;//application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/sale/exclusive/main.jrxml");

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

            if (application.getOperationType().getCode().equals(OperationType.SELL)/* || isNull(application.getApplicationPurchaseData())*/) {
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
            jasperPrintList.add(jasperPrintValid);
            //jasperPrintList.add(jasperPrintResp);
            jasperPrintList.add(jasperPrintFinal);
            jasperPrintList.add(jasperPrintActView);
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

    @Override
    public Long missContract(ContractFormDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationContract contract = saveContract(dto, application, contractStatusRepository.getOne(ContractStatus.MISSING));
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
        contract = contractRepository.save(contract);
        return contract;
    }
}
