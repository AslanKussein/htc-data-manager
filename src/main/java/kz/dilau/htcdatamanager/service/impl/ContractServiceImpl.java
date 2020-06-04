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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    private List<Employee> empList = Arrays.asList(
            new Employee(1, "Sandeep", "Data Matrix", "Front-end Developer", 20000),
            new Employee(2, "Prince", "Genpact", "Consultant", 40000),
            new Employee(3, "Gaurav", "Silver Touch ", "Sr. Java Engineer", 47000),
            new Employee(4, "Abhinav", "Akal Info Sys", "CTO", 700000));

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
    public String generateReport() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report.jrxml");

            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(empList);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "vitrina");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    jrBeanCollectionDataSource);

            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            String base64String = Base64.encodeBase64String(bytes);

            log.info("Done");

            return base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
