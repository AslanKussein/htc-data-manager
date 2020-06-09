package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationContractRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {
    private final ApplicationContractRepository contractRepository;
    private final ApplicationService applicationService;
    private final ResourceLoader resourceLoader;

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
        try {
            if (application.getOperationType().getCode().equals(OperationType.SELL) || isNull(application.getApplicationPurchaseData())) {
                throw BadRequestException.idMustNotBeNull();
            }
            City city = application.getApplicationPurchaseData().getCity();
            Resource resource = resourceLoader.getResource("classpath:jasper/" + dto.getGuid());

            InputStream input = resource.getInputStream();

            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("contractNumber", dto.getContractNumber());
            parameters.put("city", nonNull(city) ? city.getMultiLang().getNameRu() : "");
            parameters.put("printDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            parameters.put("clientFullname", application.getClientLogin());
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
}
