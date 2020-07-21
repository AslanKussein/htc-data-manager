package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.*;
import kz.dilau.htcdatamanager.repository.filter.ApplicationSpecifications;
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
import org.springframework.data.jpa.domain.Specification;
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

    private static final String APPLICATION_GROUP = "APPLICATION_GROUP";
    private static final String REAL_PROPERTY_GROUP = "REAL_PROPERTY_GROUP";
    private static final String AGENT_GROUP = "AGENT_GROUP";
    private static final String CLIENT_GROUP = "CLIENT_GROUP";

    private static final String SALE_DEAL_INFO = "SALE_DEAL_INFO";
    private static final String PURCHASE_DEAL_INFO = "PURCHASE_DEAL_INFO";
    private static final String DEAL_DATA = "DEAL_DATA";

    private static final String SALE_OBJECT_INFO = "SALE_OBJECT_INFO";
    private static final String PURCHASE_OBJECT_INFO = "PURCHASE_OBJECT_INFO";
    private static final String SALE_OBJECT_DATA = "SALE_OBJECT_DATA";

    private static final List<String> APP_OPERATIONS = Arrays.asList(APPLICATION_GROUP, REAL_PROPERTY_GROUP, AGENT_GROUP, CLIENT_GROUP);

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

    public String getAppointmentAgent(String agent) {
        return isNull(agent) || agent.equals("") ? getAuthorName() : agent;
    }

    public List<String> getOperationList(String token, Application application) {
        List<String> result = new ArrayList<>();
        OperationFilterDto filterDto = OperationFilterDto.builder()
                .author(application.getCreatedBy())
                .currentAgent(application.getCurrentAgent())
                .operationGroupList(APP_OPERATIONS)
                .build();
        ListResponse<String> operations = keycloakService.getOperationsByRole(token, filterDto);
        if (nonNull(operations) && nonNull(operations.getData()) && !operations.getData().isEmpty()) {
            result.addAll(operations.getData());
        }
        return result;
    }

    @Override
    public ApplicationDto getById(final String token, Long id) {
        Application application = getApplicationById(id);
        List<String> operations = getOperationList(token, application);
        return mapToApplicationDto(application, operations);
    }

    private ApplicationDto mapToApplicationDto(Application application, List<String> operations) {
        ApplicationDto dto = new ApplicationDto();
        dto.setOperationList(operations);
        dto.setAgent(application.getCurrentAgent());
        dto.setClientLogin(application.getClientLogin());
        dto.setOperationTypeId(application.getOperationTypeId());
        dto.setObjectTypeId(application.getObjectTypeId());
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData()) && operations.contains(VIEW + SALE_DEAL_INFO)) {
            dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
        }
        if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData()) && operations.contains(VIEW + PURCHASE_DEAL_INFO)) {
            dto.setPurchaseDataDto(new ApplicationPurchaseDataDto(application.getApplicationPurchaseData()));
        }
        if (operations.contains(VIEW + DEAL_DATA)) {
            dto.setContractDto(new ContractFormDto(application.getContract()));
        }
        RealPropertyDto realPropertyDto = new RealPropertyDto();
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData())
                && nonNull(application.getApplicationSellData().getRealProperty())) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            if (operations.contains(VIEW + SALE_OBJECT_INFO)) {
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
            }
            if (operations.contains(VIEW + SALE_OBJECT_DATA)) {
                realPropertyDto.setCadastralNumber(realProperty.getCadastralNumber());
                realPropertyDto.setApartmentNumber(realProperty.getApartmentNumber());
            }
        } else if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData())
                && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
            if (operations.contains(VIEW + PURCHASE_OBJECT_INFO)) {
                dto.setPurchaseInfoDto(new PurchaseInfoDto(application.getApplicationPurchaseData().getPurchaseInfo()));
            }
        }
        dto.setRealPropertyDto(realPropertyDto);
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
        if (operationType.isBuy()) {
            ApplicationPurchaseData data = new ApplicationPurchaseData(application, dto.getNote());
            application.setApplicationPurchaseData(data);
        } else if (operationType.isSell()) {
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
        if (nonNull(application.getCurrentAgent()) && application.getCurrentAgent().equals(dto.getAgent())) {
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

    public boolean checkOperation(List<String> operations, String operation) {
        return isNull(operations) || operations.contains(operation);
    }

    @Transactional
    public Long saveApplication(String token, Application application, ApplicationDto dto) {
        List<String> operations = null;
        RealPropertyMetadata metadata = null;
        RealPropertyFile realPropertyFile = null;
        OperationType operationType;
        ApplicationSource applicationSource;
        if (nonNull(application.getId())) {
            operationType = application.getOperationType();
            operations = getOperationList(token, application);
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
        if (operationType.isBuy() && nonNull(dto.getPurchaseDataDto())) {
            if (checkOperation(operations, UPDATE + PURCHASE_DEAL_INFO)) {
                ApplicationPurchaseData purchaseData = entityMappingTool.convertApplicationPurchaseData(dto);
                if (checkOperation(operations, UPDATE + PURCHASE_OBJECT_INFO)) {
                    PurchaseInfo info = entityMappingTool.convertPurchaseInfo(dto);
                    if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData()) &&
                            nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
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
        } else if (operationType.isSell()) {
            if (nonNull(dto.getSellDataDto()) && checkOperation(operations, UPDATE + SALE_DEAL_INFO)) {
                ApplicationSellDataDto dataDto = dto.getSellDataDto();
                if (isNull(dataDto.getObjectPrice())) {
                    throw BadRequestException.createRequiredIsEmpty("ObjectPrice");
                }
                ApplicationSellData sellData = new ApplicationSellData(dataDto);
                RealPropertyDto realPropertyDto = dto.getRealPropertyDto();
                if (checkOperation(operations, UPDATE + SALE_OBJECT_DATA) && nonNull(realPropertyDto) && nonNull(realPropertyDto.getBuildingDto())) {
                    RealProperty realProperty = null;
                    Building building = buildingService.getByPostcode(realPropertyDto.getBuildingDto().getPostcode());
                    realPropertyFile = new RealPropertyFile(realPropertyDto);
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
                    if (checkOperation(operations, UPDATE + SALE_OBJECT_INFO)) {
                        metadata = entityMappingTool.convertRealPropertyMetadata(realPropertyDto);
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
                                    } else {
                                        metadata.setMetadataStatus(approved);
                                    }
                                } else {
                                    metadata.setMetadataStatus(notApproved);
                                }
                            } else if (nonNull(metadataByStatus)) {
                                metadata = metadataByStatus;
                            }
                            RealPropertyFile filesByStatus = realProperty.getFileByStatus(MetadataStatus.APPROVED);
                            if (realPropertyDto.getFilesEdited() && !realPropertyFile.getFilesMap().isEmpty()) {
                                if (nonNull(application.getId()) && actualSellDataList.size() == 1 && actualSellDataList.get(0).getApplication().getId().equals(application.getId())) {
                                    if (nonNull(filesByStatus)) {
                                        realPropertyFile.setId(filesByStatus.getId());
                                        realPropertyFile.setMetadataStatus(filesByStatus.getMetadataStatus());
                                    } else {
                                        realPropertyFile.setMetadataStatus(approved);
                                    }
                                } else {
                                    realPropertyFile.setMetadataStatus(notApproved);
                                }
                            } else if (nonNull(filesByStatus)) {
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

        applicationSource = entityService.mapRequiredEntity(ApplicationSource.class, ApplicationSource.CRM);
        application.setApplicationSource(applicationSource);
        application = applicationRepository.save(application);
        if (nonNull(metadata)) {
            metadataRepository.save(metadata);
        }
        if (nonNull(realPropertyFile) && !realPropertyFile.getFilesMap().isEmpty()) {
            fileRepository.save(realPropertyFile);
        }
        return application.getId();
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
                    UserInfoDto userInfoDto = nonNull(item.getApplication().getCurrentAgent()) ? userInfoDtoMap.get(item.getApplication().getCurrentAgent()) : null;
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
            return null;
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
    public Long approveReserve(Long applicationId) {
        Application application = getApplicationById(applicationId);
        String author = getAuthorName();
        if (!application.getCreatedBy().equalsIgnoreCase(author) && (isNull(application.getCurrentAgent()) || !application.getCurrentAgent().equalsIgnoreCase(author))) {
            throw BadRequestException.createTemplateException("error.has.not.permission");
        }
        if (!application.getOperationType().isSell()) {
            throw BadRequestException.createTemplateException("error.application.operation.type.to.reserve");
        }
        if (isNull(application.getApplicationSellData()) || isNull(application.getApplicationSellData().getRealProperty())) {
            throw BadRequestException.createTemplateExceptionWithParam("error.real.property.not.found", applicationId.toString());
        }
        if (nonNull(application.getApplicationSellData().getRealProperty().getIsReserved()) && application.getApplicationSellData().getRealProperty().getIsReserved()) {
            throw BadRequestException.createTemplateException("error.real.property.reserve.exist");
        }
        Specification<Application> specification = ApplicationSpecifications.isRemovedEquals(false)
                .and(ApplicationSpecifications.targetApplicationIdEquals(applicationId)
                        .and(ApplicationSpecifications.applicationStatusIdNotIn(ApplicationStatus.CLOSED_STATUSES)));
        if (applicationRepository.findAll(specification).isEmpty()) {
            throw BadRequestException.createTemplateException("error.target.application.not.found");
        }
        application.getApplicationSellData().getRealProperty().setIsReserved(true);
        applicationRepository.save(application);
        return application.getId();
    }

    @Override
    public List<String> getOperationsByAppId(String token, Long applicationId) {
        Application application = getApplicationById(applicationId);
        return getOperationList(token, application);
    }
}
