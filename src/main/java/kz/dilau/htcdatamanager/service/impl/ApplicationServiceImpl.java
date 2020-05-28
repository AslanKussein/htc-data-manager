package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.*;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.BuildingService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.KeycloakService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.util.EntityMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final String VIEW = "VIEW_";
    private static final String UPDATE = "UPDATE_";
    private static final Long AUTHOR = 2L;
    private static final Long AGENT = 3L;

    private static final String APPLICATION_GROUP = "APPLICATION_GROUP";
    private static final String SALE_DEAL_INFO = "SALE_DEAL_INFO";
    private static final String PURCHASE_DEAL_INFO = "PURCHASE_DEAL_INFO";
    private static final String DEAL_DATA = "DEAL_DATA";

    private static final String REAL_PROPERTY_GROUP = "REAL_PROPERTY_GROUP";
    private static final String SALE_OBJECT_INFO = "SALE_OBJECT_INFO";
    private static final String PURCHASE_OBJECT_INFO = "PURCHASE_OBJECT_INFO";
    private static final String SALE_OBJECT_DATA = "SALE_OBJECT_DATA";

    private static final List<String> APP_OPERATIONS = Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP");

    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final BuildingService buildingService;
    private final RealPropertyRepository realPropertyRepository;
    private final DataProperties dataProperties;
    private final EntityMappingTool entityMappingTool;
    private final KeycloakService keycloakService;
    private final RealPropertyMetadataRepository metadataRepository;
    private final RealPropertyFileRepository fileRepository;

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

    private List<String> getOperationList(String token, Application application) {
        List<String> operations = new ArrayList<>();
        ListResponse<CheckOperationGroupDto> checkOperationList = keycloakService.getCheckOperationList(token, APP_OPERATIONS);
        if (nonNull(checkOperationList) && nonNull(checkOperationList.getData())) {
            checkOperationList
                    .getData()
                    .stream()
                    .filter(operation -> nonNull(operation.getOperations()) && !operation.getOperations().isEmpty())
                    .map(CheckOperationGroupDto::getOperations)
                    .forEach(operations::addAll);
        }
        String authorName = getAuthorName();
        if (nonNull(authorName) && authorName.equals(application.getCurrentAgent())) {
            RoleDto roleDto = keycloakService.readRole(AUTHOR);
            if (nonNull(roleDto) && nonNull(roleDto.getOperations()) && !roleDto.getOperations().isEmpty()) {
                operations.addAll(roleDto.getOperations());
            }
        }
        if (nonNull(authorName) && authorName.equals(application.getCreatedBy())) {
            RoleDto roleDto = keycloakService.readRole(AGENT);
            if (nonNull(roleDto) && nonNull(roleDto.getOperations()) && !roleDto.getOperations().isEmpty()) {
                operations.addAll(roleDto.getOperations());
            }
        }
        return operations;
    }

    @Override
    public ApplicationDto getById(final String token, Long id) {
        Application application = getApplicationById(id);
        ListResponse<CheckOperationGroupDto> operationList = keycloakService.getCheckOperationList(token, APP_OPERATIONS);
        return mapToApplicationDto(application, operationList);
    }

    private ApplicationDto mapToApplicationDto(Application application, ListResponse<CheckOperationGroupDto> operationList) {
        ApplicationDto dto = new ApplicationDto();
        dto.setAgent(application.getCurrentAgent());
        dto.setClientLogin(application.getClientLogin());
        List<String> operations;
        for (val checkOperationGroupDto : operationList.getData()) {
            operations = checkOperationGroupDto.getOperations();
            switch (checkOperationGroupDto.getCode()) {
                case APPLICATION_GROUP:
                    for (String oper : operations) {
                        dto.setOperationTypeId(application.getOperationTypeId());
                        dto.setObjectTypeId(application.getObjectTypeId());
                        if (application.getOperationType().getCode().equals(OperationType.SELL) && nonNull(application.getApplicationSellData())) {
                            if ((VIEW + SALE_DEAL_INFO).equals(oper)) {
                                dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
                            }
                        } else if (application.getOperationType().getCode().equals(OperationType.BUY) && nonNull(application.getApplicationPurchaseData())) {
                            if ((VIEW + PURCHASE_DEAL_INFO).equals(oper)) {
                                dto.setPurchaseDataDto(new ApplicationPurchaseDataDto(application.getApplicationPurchaseData()));
                            }
                        }
                        if ((VIEW + DEAL_DATA).equals(oper)) {
                            //todo contract info
                        }
                    }
                    break;
                case REAL_PROPERTY_GROUP:
                    RealPropertyDto realPropertyDto = new RealPropertyDto();
                    for (String oper : operations) {
                        if (application.getOperationType().getCode().equals(OperationType.SELL) && nonNull(application.getApplicationSellData())
                                && nonNull(application.getApplicationSellData().getRealProperty())) {
                            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
                            if ((VIEW + SALE_OBJECT_INFO).equals(oper)) {
                                realPropertyDto.setBuildingDto(new BuildingDto(realProperty.getBuilding()));
                                RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
                                if (nonNull(metadata)) {
                                    realPropertyDto.setMetadataId(metadata.getId());
                                    realPropertyDto.setFloor(metadata.getFloor());
                                    realPropertyDto.setNumberOfRooms(metadata.getNumberOfRooms());
                                    realPropertyDto.setNumberOfBedrooms(metadata.getNumberOfBedrooms());
                                    realPropertyDto.setTotalArea(metadata.getTotalArea());
                                    realPropertyDto.setLivingArea(metadata.getLivingArea());
                                    realPropertyDto.setKitchenArea(metadata.getKitchenArea());
                                    realPropertyDto.setBalconyArea(metadata.getBalconyArea());
                                    realPropertyDto.setSewerageId(metadata.getSewerageId());
                                    realPropertyDto.setHeatingSystemId(metadata.getHeatingSystemId());
                                    realPropertyDto.setLandArea(metadata.getLandArea());
                                    realPropertyDto.setAtelier(metadata.getAtelier());
                                    realPropertyDto.setSeparateBathroom(metadata.getSeparateBathroom());
                                    realPropertyDto.setGeneralCharacteristicsDto(new GeneralCharacteristicsDto(metadata.getGeneralCharacteristics()));
                                }
                                RealPropertyFile realPropertyFile = realProperty.getFileByStatus(MetadataStatus.APPROVED);
                                if (nonNull(realPropertyFile)) {
                                    realPropertyDto.setPhotoIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.PHOTO));
                                    realPropertyDto.setHousingPlanImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN));
                                    realPropertyDto.setVirtualTourImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR));
                                }
                            } else if ((VIEW + SALE_OBJECT_DATA).equals(oper)) {
                                realPropertyDto.setCadastralNumber(realProperty.getCadastralNumber());
                                realPropertyDto.setApartmentNumber(realProperty.getApartmentNumber());
                            }
                        } else if (application.getOperationType().getCode().equals(OperationType.BUY) && nonNull(application.getApplicationPurchaseData())
                                && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
                            if ((VIEW + PURCHASE_OBJECT_INFO).equals(oper)) {
                                dto.setPurchaseInfoDto(new PurchaseInfoDto(application.getApplicationPurchaseData().getPurchaseInfo()));
                            }
                        }
                    }
                    dto.setRealPropertyDto(realPropertyDto);
                    break;
            }
        }
        return dto;
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
    public Long save(String token, ApplicationDto dto) {
        return saveApplication(token, new Application(), dto);
    }

    @Override
    public Long saveLightApplication(ApplicationLightDto dto) {
        String agent = getAppointmentAgent(dto.getAgent());
        OperationType operationType = entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId());
        Application application = Application.builder()
                .operationType(operationType)
                .objectType(entityService.mapRequiredEntity(ObjectType.class, dto.getObjectTypeId()))
                .applicationStatus(applicationStatusRepository.getOne(ApplicationStatus.FIRST_CONTACT))
                .currentAgent(agent)
                .clientLogin(dto.getClientLogin())
                .build();
        if (operationType.getCode().equals(OperationType.BUY)) {
            ApplicationPurchaseData data = new ApplicationPurchaseData(application, dto.getNote());
            application.setApplicationPurchaseData(data);
        } else if (operationType.getCode().equals(OperationType.SELL)) {
            ApplicationSellData data = new ApplicationSellData(application, dto.getNote());
            application.setApplicationSellData(data);
        }
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

    @Transactional
    public Long saveApplication(String token, Application application, ApplicationDto dto) {
        ListResponse<CheckOperationGroupDto> checkOperationList = keycloakService.getCheckOperationList(token, APP_OPERATIONS);
        String authorName = getAuthorName();
        List<String> operations = new ArrayList<>();
        if (nonNull(checkOperationList) && nonNull(checkOperationList.getData())) {
            checkOperationList.getData()
                    .forEach(item -> operations.addAll(item.getOperations()));
        }
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

            if (isNull(dto.getClientLogin()) || dto.getClientLogin().isEmpty()) {
                throw BadRequestException.createRequiredIsEmpty("ClientLogin");
            }
            application.setClientLogin(dto.getClientLogin());
            application.setOperationType(operationType);

            ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.FIRST_CONTACT);
            application.setApplicationStatus(status);
            application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(status)
                    .build());

            application.setObjectType(entityService.mapRequiredEntity(ObjectType.class, dto.getObjectTypeId()));
        }
        if (operationType.getCode().equals(OperationType.BUY) && nonNull(dto.getPurchaseDataDto())) {
            if (canEdit(operations, (UPDATE + PURCHASE_DEAL_INFO), application, authorName)) {
                ApplicationPurchaseData purchaseData = entityMappingTool.convertApplicationPurchaseData(dto);
                if (canEdit(operations, (UPDATE + PURCHASE_OBJECT_INFO), application, authorName)) {
                    PurchaseInfo info = entityMappingTool.convertPurchaseInfo(dto);
                    if (nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
                        info.setId(application.getApplicationPurchaseData().getPurchaseInfo().getId());
                    }
                    purchaseData.setPurchaseInfo(info);
                }
                if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData())) {
                    purchaseData.setId(application.getApplicationPurchaseData().getId());
                }
                purchaseData.setApplication(application);
                application.setApplicationPurchaseData(purchaseData);
            }
        } else if (operationType.getCode().equals(OperationType.SELL)) {
            if (nonNull(dto.getSellDataDto()) && canEdit(operations, (UPDATE + SALE_DEAL_INFO), application, authorName)) {
                ApplicationSellDataDto dataDto = dto.getSellDataDto();
                if (isNull(dataDto.getObjectPrice())) {
                    throw BadRequestException.createRequiredIsEmpty("ObjectPrice");
                }
                ApplicationSellData sellData = new ApplicationSellData(dataDto);
                RealPropertyDto realPropertyDto = dto.getRealPropertyDto();
                if (canEdit(operations, (UPDATE + SALE_OBJECT_DATA), application, authorName) && nonNull(realPropertyDto) && nonNull(realPropertyDto.getBuildingDto())) {
                    RealProperty realProperty = null;
                    Building building = buildingService.getByPostcode(realPropertyDto.getBuildingDto().getPostcode());
                    RealPropertyFile realPropertyFile = new RealPropertyFile(realPropertyDto);
                    MetadataStatus notApproved = entityService.mapEntity(MetadataStatus.class, MetadataStatus.NOT_APPROVED);
                    MetadataStatus approved = entityService.mapEntity(MetadataStatus.class, MetadataStatus.APPROVED);
                    if (nonNull(building)) {
                        realProperty = realPropertyRepository.findByApartmentNumberAndBuildingId(realPropertyDto.getApartmentNumber(), building.getId());
                    } else {
                        building = entityMappingTool.convertBuilding(realPropertyDto.getBuildingDto());
                    }
                    if (isNull(realProperty)) {
                        realProperty = new RealProperty(realPropertyDto, building);
                    }
                    if (canEdit(operations, (UPDATE + SALE_OBJECT_INFO), application, authorName)) {
                        RealPropertyMetadata metadata = entityMappingTool.convertRealPropertyMetadata(realPropertyDto);
                        if (nonNull(realProperty.getId())) {
                            List<ApplicationSellData> actualSellDataList = realProperty.getActualSellDataList();
                            if (actualSellDataList.size() >= dataProperties.getMaxApplicationCountForOneRealProperty()) {
                                if (isNull(application.getApplicationSellData()) || isNull(application.getApplicationSellData().getRealProperty())
                                        || !application.getApplicationSellData().getRealProperty().getId().equals(realProperty.getId())) {
                                    throw BadRequestException.createMaxApplicationCount(realPropertyDto.getApartmentNumber(), building.getPostcode());
                                }
                            }
                            RealPropertyMetadata metadataByStatus = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
                            if (realPropertyDto.getEdited()) {
                                if (nonNull(application.getId()) && actualSellDataList.size() == 1 && actualSellDataList.get(0).getApplication().getId().equals(application.getId())) {
                                    if (nonNull(metadataByStatus)) {
                                        metadata.setId(metadataByStatus.getId());
                                        metadata.setMetadataStatus(metadataByStatus.getMetadataStatus());
                                    }
                                } else {
                                    metadata.setMetadataStatus(notApproved);
                                }
                            } else {
                                metadata = metadataByStatus;
                            }
                            RealPropertyFile filesByStatus = realProperty.getFileByStatus(MetadataStatus.APPROVED);
                            if (realPropertyDto.getFilesEdited() && !realPropertyFile.getFilesMap().isEmpty()) {
                                if (nonNull(application.getId()) && actualSellDataList.size() == 1 && actualSellDataList.get(0).getApplication().getId().equals(application.getId())) {
                                    if (nonNull(filesByStatus)) {
                                        realPropertyFile.setId(filesByStatus.getId());
                                        realPropertyFile.setMetadataStatus(filesByStatus.getMetadataStatus());
                                    }
                                } else {
                                    realPropertyFile.setMetadataStatus(notApproved);
                                }
                            } else {
                                realPropertyFile = filesByStatus;
                            }
                        } else {
                            metadata.setMetadataStatus(approved);
                            realPropertyFile.setMetadataStatus(approved);
                        }
                        metadata.setRealProperty(realProperty);
                        metadata.setApplication(application);
                        realPropertyFile.setRealProperty(realProperty);
                        realPropertyFile.setApplication(application);
//                        metadataRepository.save(metadata);
//                        fileRepository.save(realPropertyFile);
                        realProperty.getMetadataList().add(metadata);
                        realProperty.getFileList().add(realPropertyFile);
                    }
                    sellData.setRealProperty(realProperty);
                }
                sellData.setApplication(application);
                if (nonNull(application.getId()) && nonNull(application.getApplicationSellData())) {
                    sellData.setId(application.getApplicationSellData().getId());
                }
                application.setApplicationSellData(sellData);
            }
        }
        application = applicationRepository.save(application);
        return application.getId();
    }

    private boolean canEdit(List<String> operations, String operation, Application application, String authorName) {
        if (isNull(application.getId()) || application.getClientLogin().equals(authorName) || application.getCurrentAgent().equals(authorName)) {
            return true;
        }
        return operations.contains(operation);
    }

    @Override
    public Long update(String token, Long id, ApplicationDto input) {
        Application application = getApplicationById(id);
        return saveApplication(token, application, input);
    }

    @Override
    public Long deleteById(String token, Long id) {
        Application application = getApplicationById(id);
        application.setIsRemoved(true);
        return applicationRepository.save(application).getId();
    }

    @Override
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
    public MetadataWithApplicationsDto getApartmentByNumberAndPostcode(String apartmentNumber, String postcode) {
        RealProperty realProperty = realPropertyRepository.findByApartmentNumberAndPostcode(apartmentNumber, postcode);
        if (nonNull(realProperty)) {
            if (realProperty.getActualSellDataList().size() >= dataProperties.getMaxApplicationCountForOneRealProperty()) {
                throw BadRequestException.createMaxApplicationCount(apartmentNumber, postcode);
            }
            List<ApplicationSellData> sellDataList = realProperty.getActualSellDataList();
            List<ApplicationByRealPropertyDto> applicationByRealPropertyDtoList = new ArrayList<>();
            if (!sellDataList.isEmpty()) {
                Set<String> agents = sellDataList.stream().map(item -> item.getApplication().getCurrentAgent()).collect(Collectors.toSet());
                Map<String, UserInfoDto> userInfoDtoMap = keycloakService.mapUserInfos(new ArrayList<>(agents));
                for (val item : sellDataList) {
                    UserInfoDto userInfoDto = userInfoDtoMap.get(item.getApplication().getCurrentAgent());
                    applicationByRealPropertyDtoList.add(ApplicationByRealPropertyDto.builder()
                            .id(item.getApplication().getId())
                            .creationDate(item.getApplication().getCreatedDate())
                            .agent(nonNull(userInfoDto) ? userInfoDto.getFullname() : null)
                            .objectPrice(item.getObjectPrice())
                            .build());
                }
            }
            return MetadataWithApplicationsDto.builder()
                    .realPropertyDto(new RealPropertyDto(realProperty))
                    .applicationByRealPropertyDtoList(applicationByRealPropertyDtoList)
                    .build();
        } else {
            throw NotFoundException.createApartmentByNumberAndPostcode(apartmentNumber, postcode);
        }
    }

    @Override
    public Page<ApplicationDto> getNotApprovedMetadata(Pageable pageable) {
        Page<Application> applications = applicationRepository.findAllByMetadataStatus(MetadataStatus.NOT_APPROVED, pageable);
        if (nonNull(applications) && !applications.isEmpty()) {
            return applications.map(this::mapMetadataToAppicationDto);
        } else {
            return null;
        }
    }

    @Override
    public Page<ApplicationDto> getNotApprovedFiles(Pageable pageable) {
        Page<Application> applications = applicationRepository.findAllFileByMetadataStatus(MetadataStatus.NOT_APPROVED, pageable);
        if (nonNull(applications) && !applications.isEmpty()) {
            return applications.map(this::mapMetadataToAppicationDto);
        } else {
            return null;
        }
    }

    @Override
    public Long approveMetadata(Long applicationId, Long statusId) {
        Application application = getApplicationById(applicationId);
        if (nonNull(application.getApplicationSellData()) && nonNull(application.getApplicationSellData().getRealProperty())) {
            RealPropertyMetadata metadata = application.getApplicationSellData().getRealProperty().getMetadataByStatusAndApplication(MetadataStatus.NOT_APPROVED, applicationId);
            if (statusId.equals(MetadataStatus.APPROVED)) {
                List<RealPropertyMetadata> approvedMetadataList = application.getApplicationSellData().getRealProperty().getMetadataListByStatus(MetadataStatus.APPROVED);
                if (nonNull(metadata)) {
                    metadata.setMetadataStatus(entityService.mapEntity(MetadataStatus.class, MetadataStatus.APPROVED));
                    metadataRepository.save(metadata);
                    MetadataStatus archive = entityService.mapEntity(MetadataStatus.class, MetadataStatus.ARCHIVE);
                    for (val data : approvedMetadataList) {
                        data.setMetadataStatus(archive);
                        metadataRepository.save(data);
                    }
                } else {
                    throw NotFoundException.createEntityNotFoundById("RealPropertyMetadata", statusId);
                }
            } else if (statusId.equals(MetadataStatus.REJECTED)) {
                metadata.setMetadataStatus(entityService.mapEntity(MetadataStatus.class, MetadataStatus.REJECTED));
                metadataRepository.save(metadata);
            }
        } else {
            throw NotFoundException.createEntityNotFoundById("RealPropertyMetadata", statusId);
        }
        return applicationId;
    }

    @Override
    public Long approveFiles(Long applicationId, Long statusId) {
        Application application = getApplicationById(applicationId);
        if (nonNull(application.getApplicationSellData()) && nonNull(application.getApplicationSellData().getRealProperty())) {
            RealPropertyFile realPropertyFile = application.getApplicationSellData().getRealProperty().getFileByStatusAndApplication(MetadataStatus.NOT_APPROVED, applicationId);
            if (statusId.equals(MetadataStatus.APPROVED)) {
                List<RealPropertyFile> approvedFiles = application.getApplicationSellData().getRealProperty().getFilesByStatus(MetadataStatus.APPROVED);
                if (nonNull(realPropertyFile)) {
                    realPropertyFile.setMetadataStatus(entityService.mapEntity(MetadataStatus.class, MetadataStatus.APPROVED));
                    fileRepository.save(realPropertyFile);
                    MetadataStatus archive = entityService.mapEntity(MetadataStatus.class, MetadataStatus.ARCHIVE);
                    for (val data : approvedFiles) {
                        data.setMetadataStatus(archive);
                        fileRepository.save(data);
                    }
                } else {
                    throw NotFoundException.createEntityNotFoundById("RealPropertyFile", statusId);
                }
            } else if (statusId.equals(MetadataStatus.REJECTED)) {
                realPropertyFile.setMetadataStatus(entityService.mapEntity(MetadataStatus.class, MetadataStatus.REJECTED));
                fileRepository.save(realPropertyFile);
            }
        } else {
            throw NotFoundException.createEntityNotFoundById("RealPropertyFile", statusId);
        }
        return applicationId;
    }

    private ApplicationDto mapMetadataToAppicationDto(Application application) {
        ApplicationDto applicationDto = ApplicationDto.builder()
                .id(application.getId())
                .operationTypeId(application.getOperationTypeId())
                .objectTypeId(application.getObjectTypeId())
                .agent(application.getCurrentAgent())
                .clientLogin(application.getClientLogin())
                .build();
        ApplicationSellData sellData = application.getApplicationSellData();
        ApplicationSellDataDto sellDataDto = new ApplicationSellDataDto(sellData);
        applicationDto.setSellDataDto(sellDataDto);
        if (nonNull(sellData.getRealProperty())) {
            RealPropertyDto realPropertyDto = new RealPropertyDto(sellData.getRealProperty(), application.getId());
            applicationDto.setRealPropertyDto(realPropertyDto);
        }
        return applicationDto;
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
