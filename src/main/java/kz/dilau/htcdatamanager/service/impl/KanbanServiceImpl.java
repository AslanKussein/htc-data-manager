package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.*;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class KanbanServiceImpl implements KanbanService {
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationService applicationService;
    private final KeycloakService keycloakService;
    private final ContractService contractService;

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
        ApplicationStatus applicationStatus = entityService.mapEntity(ApplicationStatus.class, dto.isApprove() ? ApplicationStatus.APPROVAL_FOR_SUCCESS : ApplicationStatus.APPROVAL_FOR_FAILED);
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
            applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.FAILED);
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
    public CompleteApplicationDto applicationInfo(Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        List<String> logins = new ArrayList<>();
        logins.add(application.getClientLogin());
        List<ProfileClientDto> profileClientDtoList = keycloakService.readClientInfoByLogins(logins);
        ProfileClientDto clientDto = nonNull(profileClientDtoList) && !profileClientDtoList.isEmpty() ? profileClientDtoList.get(0) : null;
        UserInfoDto agentDto = null;
        if (nonNull(application.getCurrentAgent())) {
            logins.clear();
            logins.add(application.getCurrentAgent());
            ListResponse<UserInfoDto> response = keycloakService.readUserInfos(logins);
            if (nonNull(response) && nonNull(response.getData()) && !response.getData().isEmpty()) {
                agentDto = response.getData().get(0);
            }
        }
        return mapToComplateApplicationDto(application, clientDto, agentDto);
    }

    private CompleteApplicationDto mapToComplateApplicationDto(Application application, ProfileClientDto clientDto, UserInfoDto agentDto) {
        CompleteApplicationDto result = CompleteApplicationDto.builder()
                .id(application.getId())
                .agentLogin(application.getCurrentAgent())
                .agentFullname(nonNull(agentDto) ? agentDto.getFullname() : "")
                .clientLogin(application.getClientLogin())
                .clientFullname(nonNull(clientDto) ? clientDto.getFullname() : "")
                .operationType(DictionaryMappingTool.mapMultilangDictionary(application.getOperationType()))
                .objectType(DictionaryMappingTool.mapMultilangDictionary(application.getObjectType()))
                .status(DictionaryMappingTool.mapMultilangDictionary(application.getApplicationStatus()))
                .contractGuid(nonNull(application.getContract()) ? application.getContract().getGuid() : null)
//                .depositGuid()
                .build();
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData())) {
            ApplicationSellData sellData = application.getApplicationSellData();
            result.setObjectPrice(sellData.getObjectPrice());
            result.setCommission(contractService.getCommission(sellData.getObjectPrice().intValue(), application.getObjectTypeId()));
            if (nonNull(sellData.getRealProperty())) {
                RealProperty realProperty = sellData.getRealProperty();
                if (nonNull(sellData.getRealProperty().getBuilding())) {
                    Building building = realProperty.getBuilding();
                    MultiLangText text;
                    if (nonNull(building.getResidentialComplex())) {
                        text = new MultiLangText(building.getResidentialComplex().getHouseName());
                    } else {
                        text = DictionaryMappingTool.mapAddressToMultiLang(building, realProperty.getApartmentNumber());
                    }
                    result.setAddress(text);
                }
                RealPropertyFile file = realProperty.getFileByStatus(MetadataStatus.APPROVED);
                if (nonNull(file)) {
                    result.setPhotoIdList(file.getFilesMap().get(RealPropertyFileType.PHOTO));
                    result.setVirtualTourImageIdList(file.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR));
                    result.setHousingPlanImageIdList(file.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN));
                }
                RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
                if (nonNull(metadata)) {
                    result.setNumberOfRooms(metadata.getNumberOfRooms());
                }
            }
        } else if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData())) {
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            MultiLangText text = DictionaryMappingTool.concatMultiLangWithMultiLang(DictionaryMappingTool.mapDictionaryToText(purchaseData.getCity()), DictionaryMappingTool.mapDictionaryToText(purchaseData.getDistrict()), ", ");
            result.setAddress(text);
            if (nonNull(purchaseData.getPurchaseInfo())) {
                result.setObjectPricePeriod(new BigDecimalPeriod(purchaseData.getPurchaseInfo().getObjectPriceFrom(), purchaseData.getPurchaseInfo().getObjectPriceTo()));
                result.setNumberOfRoomsPeriod(new IntegerPeriod(purchaseData.getPurchaseInfo().getNumberOfRoomsFrom(), purchaseData.getPurchaseInfo().getNumberOfRoomsTo()));
            }

        }
        return result;
    }

    private ApplicationStatus getPrevStatus(Application application) {
        if (nonNull(application) && nonNull(application.getStatusHistoryList()) && !application.getStatusHistoryList().isEmpty() && application.getStatusHistoryList().size() > 1) {
            return application.getStatusHistoryList().get(application.getStatusHistoryList().size() - 2).getApplicationStatus();
        }
        return null;
    }
}
