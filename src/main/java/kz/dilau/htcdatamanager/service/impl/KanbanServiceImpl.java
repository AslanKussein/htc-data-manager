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
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String CHOOSE_GROUP_AGENT = "CHOOSE_GROUP_AGENT";
    private static final List<String> AGENT_GROUP = Arrays.asList("AGENT_GROUP");

    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Override
    public Long changeStatus(ChangeStatusDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId());
        if (application.getOperationType().isSell() && (dto.getStatusId().equals(ApplicationStatus.PHOTO_SHOOT) && application.getApplicationStatus().isContract() ||
                dto.getStatusId().equals(ApplicationStatus.ADS) && (application.getApplicationStatus().isContract() || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT)) ||
                dto.getStatusId().equals(ApplicationStatus.DEMO) && nonNull(application.getContract()) && (application.getApplicationStatus().isContract() || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT) || application.getApplicationStatus().getId().equals(ApplicationStatus.ADS))) ||
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
            throw BadRequestException.createTemplateException("error.empty.contract");
        }
        application.getContract().setGuid(dto.getContractGuid());
        if (application.getOperationType().isBuy() && nonNull(dto.getDepositGuid())) {
            if (isNull(application.getDeposit())) {
                throw BadRequestException.createTemplateException("error.empty.deposit.contract");
            } else {
                application.getDeposit().setGuid(dto.getDepositGuid());
            }
        }
        if (application.getApplicationStatus().getId().equals(ApplicationStatus.DEMO) || application.getOperationType().isBuy() &&
                application.getApplicationStatus().getId().equals(ApplicationStatus.DEPOSIT)) {
            ApplicationStatus applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.CLOSE_TRANSACTION);
            application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                    .applicationStatus(applicationStatus)
                    .application(application)
                    .build());
            application.setApplicationStatus(applicationStatus);
            application = applicationRepository.save(application);
            return application.getId();
        } else {
            throw BadRequestException.createTemplateExceptionWithParam("error.complete.deal.from.status", application.getApplicationStatus().getMultiLang().getNameRu());
        }
    }

    @Override
    public Long confirmComplete(ConfirmDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        if (!application.getApplicationStatus().getId().equals(ApplicationStatus.APPROVAL_FOR_SUCCESS)) {
            throw BadRequestException.createTemplateExceptionWithParam("error.complete.deal.from.status", application.getApplicationStatus().getMultiLang().getNameRu());
        }
        ApplicationStatus applicationStatus;
        if (dto.isApprove()) {
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
    public Long forceCloseDeal(String token, ForceCloseDealDto dto) {
        Application application = applicationService.getApplicationById(dto.getApplicationId());
        ApplicationStatus applicationStatus;
        if (dto.isApprove()) {
            if (application.getApplicationStatus().getId().equals(ApplicationStatus.CLOSE_TRANSACTION)) {
                applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.APPROVAL_FOR_SUCCESS);
            } else {
                throw BadRequestException.createTemplateException("error.close.application.without.status");
            }
            if (nonNull(dto.getTargetApplicationId())) {
                Application targetApplication = applicationService.getApplicationById(dto.getTargetApplicationId());
                if (application.getOperationType().getCode().equals(targetApplication.getOperationType().getCode())) {
                    throw BadRequestException.createTemplateException("error.operation.type.in.target.application");
                }
                Long sellApplicationId = getTargetApplication(application);
                if (nonNull(sellApplicationId) && !sellApplicationId.equals(dto.getTargetApplicationId())) {
                    throw BadRequestException.createTemplateException("error.target.application.exist");
                }
                if (isNull(sellApplicationId) && targetApplication.isReservedRealProperty()) {
                    throw BadRequestException.createTemplateExceptionWithParam("error.application.to.sell.deposit", dto.getTargetApplicationId().toString());
                }
                application.setTargetApplication(targetApplication);
            }
        } else {
            List<String> operations = keycloakService.getOperations(token, AGENT_GROUP);
            if (operations.contains(CHOOSE_GROUP_AGENT)) {
                applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.FAILED);
            } else {
                applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.APPROVAL_FOR_FAILED);
            }
        }
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
        if (!application.getApplicationStatus().getId().equals(ApplicationStatus.APPROVAL_FOR_FAILED)) {
            throw BadRequestException.createTemplateExceptionWithParam("error.complete.deal.from.status", application.getApplicationStatus().getMultiLang().getNameRu());
        }
        ApplicationStatus applicationStatus;
        if (dto.isApprove()) {
            applicationStatus = entityService.mapEntity(ApplicationStatus.class, ApplicationStatus.FAILED);
            if (nonNull(application.getTargetApplication()) && application.getTargetApplication().isReservedRealProperty() &&
                    nonNull(application.getTargetApplication().getApplicationSellData()) && nonNull(application.getTargetApplication().getApplicationSellData().getRealProperty())) {
                Application target = application.getTargetApplication();
                target.getApplicationSellData().getRealProperty().setIsReserved(false);
                applicationRepository.save(target);
            }
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
            agentDto = keycloakService.readUserInfo(application.getCurrentAgent());
        }
        return mapToCompleteApplicationDto(application, clientDto, agentDto);
    }

    @Override
    public CompleteTargetApplicationDto targetApplicationInfo(Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        if (application.isReservedRealProperty()) {
            throw BadRequestException.createTemplateExceptionWithParam("error.application.to.sell.deposit", applicationId.toString());
        }
        UserInfoDto agentDto = null;
        if (nonNull(application.getCurrentAgent())) {
            agentDto = keycloakService.readUserInfo(application.getCurrentAgent());
        }
        return mapToTargetApplicationDto(application, agentDto);
    }

    @Override
    public Long getTargetApplication(Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        String author = getAuthorName();
        if (nonNull(author) && (application.getCreatedBy().equalsIgnoreCase(author) || nonNull(application.getCurrentAgent()) && application.getCurrentAgent().equalsIgnoreCase(author))) {
            return getTargetApplication(application);
        } else {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
    }

    private Long getTargetApplication(Application application) {
        if (application.getOperationType().isBuy() && nonNull(application.getDeposit())) {
            return application.getDeposit().getSellApplicationId();
        } else {
            return null;
        }
    }

    private CompleteTargetApplicationDto mapToTargetApplicationDto(Application application, UserInfoDto agentDto) {
        CompleteTargetApplicationDto dto = CompleteTargetApplicationDto.builder()
                .id(application.getId())
                .operationType(DictionaryMappingTool.mapMultilangDictionary(application.getOperationType()))
                .createDate(application.getCreatedDate())
                .agentLogin(application.getCurrentAgent())
                .agentFullname(nonNull(agentDto) ? agentDto.getFullname() : "")
                .status(DictionaryMappingTool.mapMultilangDictionary(application.getApplicationStatus()))
                .build();
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData())) {
            ApplicationSellData data = application.getApplicationSellData();
            dto.setObjectPrice(data.getObjectPrice());
            if (nonNull(data.getRealProperty())) {
                RealPropertyMetadata metadata = data.getRealProperty().getMetadataByStatus(MetadataStatus.APPROVED);
                if (nonNull(metadata)) {
                    dto.setNumberOfRooms(metadata.getNumberOfRooms());
                    dto.setFloor(metadata.getFloor());
                    dto.setTotalArea(metadata.getTotalArea());
                }
                if (nonNull(data.getRealProperty().getBuilding())) {
                    dto.setDistrict(DictionaryMappingTool.mapMultilangDictionary(data.getRealProperty().getBuilding().getDistrict()));
                }
            }
        } else if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData())) {
            ApplicationPurchaseData data = application.getApplicationPurchaseData();
            dto.setDistrict(DictionaryMappingTool.mapMultilangDictionary(data.getDistrict()));
            if (nonNull(data.getPurchaseInfo())) {
                PurchaseInfo info = data.getPurchaseInfo();
                dto.setObjectPricePeriod(new BigDecimalPeriod(info.getObjectPriceFrom(), info.getObjectPriceTo()));
                dto.setNumberOfRoomsPeriod(new IntegerPeriod(info.getNumberOfRoomsFrom(), info.getNumberOfRoomsTo()));
                dto.setTotalAreaPeriod(new BigDecimalPeriod(info.getTotalAreaFrom(), info.getTotalAreaTo()));
                dto.setFloorPeriod(new IntegerPeriod(info.getFloorFrom(), info.getFloorTo()));
            }
        }
        return dto;
    }

    private CompleteApplicationDto mapToCompleteApplicationDto(Application application, ProfileClientDto clientDto, UserInfoDto agentDto) {
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
                .depositGuid(nonNull(application.getDeposit()) ? application.getDeposit().getGuid() : nonNull(application.getSellDeposit()) ? application.getSellDeposit().getGuid() : null)
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
        ApplicationStatusHistory statusHistory = application.getLastStatusHistory();
        if (statusHistory.getApplicationStatus().getId().equals(ApplicationStatus.APPROVAL_FOR_FAILED)) {
            result.setComment(statusHistory.getComment());
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
