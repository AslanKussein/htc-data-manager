package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private final ApplicationContractRepository contractRepository;
    private final ApplicationService applicationService;
    private final ResourceLoader resourceLoader;

//    private List<Employee> empList = Arrays.asList(
//            new Employee(1, "Sandeep", "Data Matrix", "Front-end Developer", 20000),
//            new Employee(2, "Prince", "Genpact", "Consultant", 40000),
//            new Employee(3, "Gaurav", "Silver Touch ", "Sr. Java Engineer", 47000),
//            new Employee(4, "Abhinav", "Akal Info Sys", "CTO", 700000));

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
    public String generateReport(String path) {
        try {

            Resource resource = resourceLoader.getResource(path);

            InputStream input = resource.getInputStream();

            JasperReport jasperReport = JasperCompileManager.compileReport(input);

//            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(empList);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "vitrina");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            String base64String = Base64.encodeBase64String(bytes);

            log.info("Done");

            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

//    public byte[] createLotAppToTheReport(VLotReportData vLot, BigDecimal winnerId) throws Exception {
//        parametersMap = new HashMap<>();
//
//        try (Connection conn = dt.getConnection()) {
//
//
//            parametersMap.put("lot_id", vLot.getLotId());
//            parametersMap.put("winner_id", winnerId);
//            parametersMap.put("DEALS_DATETIME", dateTimeToString(vLot.getDealsDatetime(), DEF_DT_VAL_SS_SXX));
//
//            if (vLot.getWinnerId() != null) {
//                UserEcp winnerEcp = userEcpRepository.findByUserId_id(vLot.getWinnerId());
//                parametersMap.put("WINNER_BID", nvl(vLot.getWinnerOrg(), ""));
//                parametersMap.put("WINNER_SIGN", nvl(vLot.getWinnerFio(), ""));
//                parametersMap.put("WINNER_SIGN_INFO", "Подписано цифровой подписью: "
//                        + dnReplace(winnerEcp.getDn()));
//                parametersMap.put("WINNER_SIGN_DATE", "Дата: " + nvl(dateTimeToString(vLot.getWinnerSignDate(), DEF_DT_VAL_SS_SXX), "") + " +06'00'");
//            } else {
//                parametersMap.put("WINNER_BID", "Не выявлен");
//                parametersMap.put("WINNER_SIGN", "");
//                parametersMap.put("WINNER_SIGN_INFO", "");
//                parametersMap.put("WINNER_SIGN_DATE", "");
//            }
//
//
//            setQRCodesByWinnerId(vLot.getLotId(), vLot.getWinnerId(), 2);
//
//            JasperTemplateEntity templEntity = jasperTemplateRepository.findByCode("appToTheReport");
//
//
//            try (InputStream jrXmlStream = new ByteArrayInputStream(templEntity.getJrxml().getBytes(StandardCharsets.UTF_8))) {
//
//                JDocumentParameter jdp = new JDocumentParameter();
//                jdp.setParametersMap(parametersMap);
//                // jdp.setBeanColDataSource(itemsJRBean);
//                byte[] pdf = getPdfByJrxmlByte(jrXmlStream, parametersMap, conn);
//                return pdf;
//            } catch (Exception e) {
//                clearParametersMap();
//                throw e;
//            } finally {
//                clearParametersMap();
//            }
//        }
//    }

    public static byte[] getPdfByJrxmlByte(InputStream input, HashMap<String, Object> parametersMap, Connection conn) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrint;

            if (conn != null) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap, conn);
            } else{
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametersMap);
            }

            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, os);
                return os.toByteArray();
            } finally {
                input.close();
            }
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
