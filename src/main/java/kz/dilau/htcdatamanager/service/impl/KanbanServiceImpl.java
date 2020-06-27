package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.ApplicationStatusHistory;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.KanbanService;
import kz.dilau.htcdatamanager.web.dto.ChangeStatusDto;
import kz.dilau.htcdatamanager.web.dto.CompleteDealDto;
import kz.dilau.htcdatamanager.web.dto.ConfirmDealDto;
import kz.dilau.htcdatamanager.web.dto.ForceCloseDealDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class KanbanServiceImpl implements KanbanService {
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationService applicationService;

    @Override
    public Long changeStatus(ChangeStatusDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId());
        if (application.getOperationType().isSell() && (dto.getStatusId().equals(ApplicationStatus.PHOTO_SHOOT) && application.getApplicationStatus().isContract() ||
                dto.getStatusId().equals(ApplicationStatus.ADS) && (application.getApplicationStatus().isContract() || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT)) ||
                dto.getStatusId().equals(ApplicationStatus.DEMO) && (application.getApplicationStatus().isContract() || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT) || application.getApplicationStatus().getId().equals(ApplicationStatus.ADS))) ||
                application.getOperationType().isBuy() && dto.getStatusId().equals(ApplicationStatus.DEMO) && application.getApplicationStatus().isContract()) {
            application.setApplicationStatus(entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId()));
            return applicationRepository.save(application).getId();
        } else {
            throw BadRequestException.createChangeStatus(application.getApplicationStatus().getCode(), status.getCode());
        }
    }

    @Override
    public Long completeDeal(CompleteDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        if (isNull(application.getContract())) {
            throw BadRequestException.createTemplateException("error.contract.form.not.found");
        }
        application.getContract().setGuid(dto.getContractGuid());
        if (application.getOperationType().isBuy() && nonNull(dto.getDepositGuid())) {
            //todo сохранение идентификатора файла задатка
        }
        ApplicationStatus applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.CLOSE_TRANSACTION);
        application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                .applicationStatus(applicationStatus)
                .application(application)
                .build());
        application.setApplicationStatus(applicationStatus);
        applicationRepository.save(application);
        return application.getId();
    }

    @Override
    public Long confirmComplete(ConfirmDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus applicationStatus;
        if (dto.getApprove()) {
            applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.SUCCESS);
            application.setConfirmDocGuid(dto.getGuid());
        } else {
            applicationStatus = getPrevStatus(application);
        }
        application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                .applicationStatus(applicationStatus)
                .application(application)
                .build());
        application.setApplicationStatus(applicationStatus);
        applicationRepository.save(application);
        return application.getId();
    }

    @Override
    public Long forceCloseDeal(ForceCloseDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.CLOSE_TRANSACTION);
        application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                .applicationStatus(applicationStatus)
                .application(application)
                .comment(dto.getComment())
                .build());
        application.setApplicationStatus(applicationStatus);
        applicationRepository.save(application);
        return application.getId();
    }

    @Override
    public Long confirmCloseDeal(ConfirmDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus applicationStatus;
        if (dto.getApprove()) {
            applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.FINISHED);
        } else {
            applicationStatus = getPrevStatus(application);
        }
        application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                .applicationStatus(applicationStatus)
                .application(application)
                .build());
        application.setApplicationStatus(applicationStatus);
        applicationRepository.save(application);
        return application.getId();
    }

    private ApplicationStatus getPrevStatus(Application application) {
        if (nonNull(application) && nonNull(application.getStatusHistoryList()) && !application.getStatusHistoryList().isEmpty() && application.getStatusHistoryList().size() > 1) {
            return application.getStatusHistoryList().get(application.getStatusHistoryList().size() - 2).getApplicationStatus();
        }
        return null;
    }
}
