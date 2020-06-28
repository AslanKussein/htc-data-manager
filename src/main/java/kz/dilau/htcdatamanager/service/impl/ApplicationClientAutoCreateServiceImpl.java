package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationSource;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyFileRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyMetadataRepository;
import kz.dilau.htcdatamanager.service.ApplicationClientAutoCreateService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.util.EntityMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationClientAutoCreateServiceImpl implements ApplicationClientAutoCreateService {

    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final EntityMappingTool entityMappingTool;
    private final RealPropertyMetadataRepository metadataRepository;
    private final RealPropertyFileRepository fileRepository;
    private final ApplicationService applicationService;

    @Override
    public Long save(ApplicationDto dto) {
        return saveApplication(new Application(), dto);
    }

    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Transactional
    public Long saveApplication(Application application, ApplicationDto dto) {
        RealPropertyMetadata metadata = null;
        RealPropertyFile realPropertyFile = null;
        OperationType operationType;
        ApplicationSource applicationSource;
        if (nonNull(application.getId())) {
            operationType = application.getOperationType();
        } else {
            operationType = entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId());

            String agent = applicationService.getAppointmentAgent(dto.getAgent());
            application.setCurrentAgent(agent);
            Assignment assignment = Assignment.builder()
                    .application(application)
                    .agent(agent)
                    .build();
            application.getAssignmentList().add(assignment);

            application.setClientLogin(getAuthorName());
            application.setOperationType(operationType);

            ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.FIRST_CONTACT);
            application.setApplicationStatus(status);
            application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(status)
                    .build());

            application.setObjectType(entityService.mapRequiredEntity(ObjectType.class, dto.getObjectTypeId()));
        }
        if (operationType.isBuy() && nonNull(dto.getPurchaseDataDto())) {
            ApplicationPurchaseData purchaseData = entityMappingTool.convertApplicationPurchaseData(dto);
            PurchaseInfo info = entityMappingTool.convertPurchaseInfo(dto);
            if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData()) &&
                    nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
                info.setId(application.getApplicationPurchaseData().getPurchaseInfo().getId());
            }
            purchaseData.setPurchaseInfo(info);

            if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData())) {
                purchaseData.setId(application.getApplicationPurchaseData().getId());
            }
            purchaseData.setApplication(application);
            application.setApplicationPurchaseData(purchaseData);
        }

        applicationSource = entityService.mapRequiredEntity(ApplicationSource.class, ApplicationSource.CRM);
        application.setApplicationSource(applicationSource);
        application = applicationRepository.save(application);
        if (nonNull(metadata)) {
            metadataRepository.save(metadata);
        }
        if (nonNull(realPropertyFile)) {
            fileRepository.save(realPropertyFile);
        }
        return application.getId();
    }

    @Override
    public Long update(Long id, ApplicationDto input) {
        Application application = applicationService.getApplicationById(id);
        return saveApplication(application, input);
    }

}
