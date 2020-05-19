package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.BuildingService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final BuildingService buildingService;

    private String getAuthorName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    private String getAppointmentAgent(String agent) {
        return isNull(agent) || agent.equals("") ? getAuthorName() : agent;
    }

    @Override
    public ApplicationDto getById(final String token, Long id) {
        Application application = getApplicationById(id);
        return mapToApplicationDto(application);
//        ApplicationDto dto = new ApplicationDto();
//        ListResponse<CheckOperationGroupDto> checkOperationList = dataAccessService.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
//        checkOperationList
//                .getData()
//                .stream()
//                .filter(e -> "APPLICATION_GROUP".equals(e.getCode()))
//                .findFirst()
//                .ifPresent(e -> {
//                    List<String> operations = e.getOperations();
//                    for (String oper : operations) {
//                        switch (oper) {
//                            case "VIEW_SALE_DEAL_INFO":
//                                dto.setId(application.getId());
//                                dto.setOperationTypeId(application.getOperationType().getId());
////                                dto.setObjectTypeId(application.getObjectType().getId());
////                                dto.setObjectPrice(application.getObjectPrice());
//                                dto.setMortgage(application.getMortgage());
//                                dto.setEncumbrance(application.getEncumbrance());
//                                dto.setSharedOwnershipProperty(application.getSharedOwnershipProperty());
//                                dto.setExchange(application.getExchange());
//                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
////                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
//                                dto.setTheSizeOfTrades(application.getTheSizeOfTrades());
//                                break;
//                            case "NOT_ACCESS_ VIEW_SALE_DEAL_INFO":
//                                break;
//                            case "VIEW_PURCHASE_DEAL_INFO":
//                                dto.setOperationTypeId(application.getOperationType().getId());
////                                dto.setObjectTypeId(application.getObjectType().getId());
////                                dto.setObjectPriceFrom(application.getObjectPriceFrom());
////                                dto.setObjectPriceTo(application.getObjectPriceTo());
//                                dto.setMortgage(application.getMortgage());
//                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
////                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
//                                break;
//                            case "NOT_ACCESS_ VIEW_PURCHASE_DEAL_INFO":
//                                break;
//                            case "VIEW_DEAL_DATA":
//                                dto.setContractPeriod(application.getContractPeriod());
////                                dto.setAmount(application.getAmount());//todo
//                                dto.setCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice());
//                                break;
//                        }
//                    }
//                });
//        return mapToApplicationDto(application);
    }

    private ApplicationDto mapToApplicationDto(Application application) {
        ApplicationDto applicationDto = ApplicationDto.builder()
                .id(application.getId())
                .operationTypeId(application.getOperationTypeId())
                .objectTypeId(application.getObjectTypeId())
                .agent(application.getCurrentAgent())
                .clientLogin(application.getClientLogin())
                .build();
        if (application.getOperationType().getCode().equals(OperationType.SELL) && nonNull(application.getApplicationSellData())) {
            ApplicationSellData sellData = application.getApplicationSellData();
            ApplicationSellDataDto sellDataDto = new ApplicationSellDataDto(sellData);
            applicationDto.setSellDataDto(sellDataDto);
            if (nonNull(sellData.getRealProperty())) {
                RealPropertyDto realPropertyDto = new RealPropertyDto(sellData.getRealProperty());
                applicationDto.setRealPropertyDto(realPropertyDto);
            }
        } else if (application.getOperationType().getCode().equals(OperationType.BUY) && nonNull(application.getApplicationPurchaseData())) {
            ApplicationPurchaseData purchaseData = application.getApplicationPurchaseData();
            ApplicationPurchaseDataDto purchaseDataDto = new ApplicationPurchaseDataDto(purchaseData);
            applicationDto.setPurchaseDataDto(purchaseDataDto);
            if (nonNull(purchaseData.getPurchaseInfo())) {
                PurchaseInfoDto infoDto = new PurchaseInfoDto(purchaseData.getPurchaseInfo());
                applicationDto.setPurchaseInfoDto(infoDto);
            }
        }
        return applicationDto;
    }

    private List<ApplicationStatusHistoryDto> mapStatusHistoryList(Application application) {
        List<ApplicationStatusHistoryDto> statusHistoryList = new ArrayList<>();
        application.getStatusHistoryList().forEach(history ->
                statusHistoryList.add(ApplicationStatusHistoryDto.builder()
                        .applicationStatus(DictionaryMappingTool.mapDictionaryToText(history.getApplicationStatus()))
                        .comment(history.getComment())
                        .creationDate(history.getCreatedDate())
                        .build()));
        return statusHistoryList;
    }

    @Override
    @Transactional
    public Long save(ApplicationDto dto) {
        return saveApplication(new Application(), dto);
    }

    @Override
    public Long saveLightApplication(ApplicationLightDto dto) {
        String agent = getAppointmentAgent(dto.getAgent());
        Application application = Application.builder()
                .operationType(entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId()))
                .applicationSellData(new ApplicationSellData(dto.getNote()))
                .applicationStatus(applicationStatusRepository.getOne(ApplicationStatus.FIRST_CONTACT))
                .currentAgent(agent)
                .build();
        Assignment assignment = Assignment.builder()
                .application(application)
                .agent(agent)
                .build();
        application.getAssignmentList().add(assignment);
        return applicationRepository.save(application).getId();
    }

    @Override
    public Long reassignApplication(AssignmentDto dto) {
        Application application = getApplicationById(dto.getApplicationId());
        if (application.getCurrentAgent().equals(dto.getAgent())) {
            throw BadRequestException.createReassignToSameAgent();
        }
        application.setCurrentAgent(dto.getAgent());
        Assignment assignment = Assignment.builder()
                .application(application)
                .agent(dto.getAgent())
                .build();
        application.getAssignmentList().add(assignment);
        return applicationRepository.save(application).getId();
    }


    private Long saveApplication(Application application, ApplicationDto dto) {
        OperationType operationType;
        if (nonNull(application.getId())) {
            operationType = application.getOperationType();
        } else {
            operationType = entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId());
//            if (operationType.getCode().equals(OperationType.SELL) && realPropertyService.existsByCadastralNumber(dto.getRealPropertyRequestDto().getCadastralNumber())) {
//                throw BadRequestException.createCadastralNumberHasFounded(dto.getRealPropertyRequestDto().getCadastralNumber());
//            }
            String agent = getAppointmentAgent(dto.getAgent());
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
        }
        application.setObjectType(entityService.mapRequiredEntity(ObjectType.class, dto.getObjectTypeId()));
        if (operationType.getCode().equals(OperationType.BUY) && nonNull(dto.getPurchaseDataDto())) {
            PurchaseInfoDto infoDto = dto.getPurchaseInfoDto();
            ApplicationPurchaseDataDto dataDto = dto.getPurchaseDataDto();
            ApplicationPurchaseData data = new ApplicationPurchaseData(dataDto, dto.getPurchaseInfoDto(),
                    entityService.mapRequiredEntity(City.class, dataDto.getCityId()), entityService.mapEntity(District.class, dataDto.getDistrictId()),
                    nonNull(infoDto) && nonNull(infoDto.getMaterialOfConstructionId()) ? entityService.mapRequiredEntity(MaterialOfConstruction.class, infoDto.getMaterialOfConstructionId()) : null,
                    nonNull(infoDto) && nonNull(infoDto.getYardTypeId()) ? entityService.mapRequiredEntity(YardType.class, infoDto.getYardTypeId()) : null);
            data.setApplication(application);
            application.setApplicationPurchaseData(data);
        } else if (operationType.getCode().equals(OperationType.SELL)) {
            if (nonNull(dto.getSellDataDto())) {
                ApplicationSellDataDto dataDto = dto.getSellDataDto();
                Building building = null;
                if (nonNull(dto.getRealPropertyDto()) && nonNull(dto.getRealPropertyDto().getBuildingDto())) {
                    building = buildingService.getByPostcode(dto.getRealPropertyDto().getBuildingDto().getPostcode());
                    if (isNull(building)) {
                        BuildingDto buildingDto = dto.getRealPropertyDto().getBuildingDto();
                        building = new Building(buildingDto,
                                entityService.mapRequiredEntity(City.class, buildingDto.getCityId()),
                                entityService.mapRequiredEntity(District.class, buildingDto.getDistrictId()),
                                entityService.mapRequiredEntity(Street.class, buildingDto.getStreetId()));
                    }
                }
                RealPropertyDto realPropertyDto = dto.getRealPropertyDto();
                RealPropertyMetadata metadata = new RealPropertyMetadata(realPropertyDto,
                        entityService.mapEntity(Sewerage.class, realPropertyDto.getSewerageId()),
                        entityService.mapEntity(HeatingSystem.class, realPropertyDto.getHeatingSystemId()),
                        entityService.mapEntity(MetadataStatus.class, realPropertyDto.getMetadataId()),
                        nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(PropertyDeveloper.class, realPropertyDto.getGeneralCharacteristicsDto().getPropertyDeveloperId()) : null,
                        nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(HouseCondition.class, realPropertyDto.getGeneralCharacteristicsDto().getHouseConditionId()) : null);
                metadata.setApplication(application);
                ApplicationSellData sellData = new ApplicationSellData(dataDto, dto.getRealPropertyDto(), building, metadata);
                sellData.setApplication(application);
                application.setApplicationSellData(sellData);
            }
        }
        return applicationRepository.save(application).getId();
    }

    @Override
    public Long update(String token, Long id, ApplicationDto input) {
        Application application = getApplicationById(id);
        return saveApplication(application, input);
    }

    @Override
    public Long deleteById(String token, Long id) {
        Application application = getApplicationById(id);
        application.setIsRemoved(true);
        return applicationRepository.save(application).getId();
    }

    public Application getApplicationById(Long id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            if (optionalApplication.get().getIsRemoved()) {
                throw EntityRemovedException.createApplicationRemoved(id);
            }
            return optionalApplication.get();
        } else {
            throw NotFoundException.createApplicationById(id);
        }
    }

    @Override
    public Long changeStatus(ChangeStatusDto dto) {
        Application application = getApplicationById(dto.getApplicationId());
        ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId());
        if (dto.getStatusId().equals(ApplicationStatus.DEMO) || dto.getStatusId().equals(ApplicationStatus.PHOTO_SHOOT) || dto.getStatusId().equals(ApplicationStatus.ADS)) {
            if (application.getApplicationStatus().getId().equals(ApplicationStatus.CONTRACT) || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT) || application.getApplicationStatus().getId().equals(ApplicationStatus.ADS)) {
                application.setApplicationStatus(entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId()));
                return applicationRepository.save(application).getId();
            } else {
                throw BadRequestException.createChangeStatus(application.getApplicationStatus().getCode(), status.getCode());
            }
        } else {
            throw BadRequestException.createChangeStatus(application.getApplicationStatus().getCode(), status.getCode());
        }
    }
}
