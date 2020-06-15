package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyMetadataRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.ApplicationClientService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.BuildingService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.util.EntityMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import kz.dilau.htcdatamanager.web.dto.BuildingDto;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.PurchaseInfoClientDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationClientServiceImpl implements ApplicationClientService {
    private static final String VIEW = "VIEW_";
    private static final String UPDATE = "UPDATE_";

    private static final String SALE_DEAL_INFO = "SALE_DEAL_INFO";
    private static final String PURCHASE_DEAL_INFO = "PURCHASE_DEAL_INFO";

    private static final String SALE_OBJECT_INFO = "SALE_OBJECT_INFO";
    private static final String PURCHASE_OBJECT_INFO = "PURCHASE_OBJECT_INFO";
    private static final String SALE_OBJECT_DATA = "SALE_OBJECT_DATA";

    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final BuildingService buildingService;
    private final RealPropertyRepository realPropertyRepository;
    private final DataProperties dataProperties;
    private final EntityMappingTool entityMappingTool;
    private final RealPropertyMetadataRepository metadataRepository;
    private final ApplicationService applicationService;


    @Override
    public ApplicationClientDTO getById(final String token, Long id) {
        Application application = applicationService.getApplicationById(id);
        List<String> operations = applicationService.getOperationList(token, application);
        return mapToApplicationClientDTO(application, operations);
    }

    private ApplicationClientDTO mapToApplicationClientDTO(Application application, List<String> operations) {
        ApplicationClientDTO dto = new ApplicationClientDTO();
        dto.setAgent(application.getCurrentAgent());
        dto.setClientLogin(application.getClientLogin());
        dto.setOperationTypeId(application.getOperationTypeId());
        dto.setObjectTypeId(application.getObjectTypeId());
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData()) && operations.contains(VIEW + SALE_DEAL_INFO)) {
            dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));
        }
        /*if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData()) && operations.contains(VIEW + PURCHASE_DEAL_INFO)) {
            dto.setPurchaseDataDto(new ApplicationPurchaseDataDto(application.getApplicationPurchaseData()));
        }*/

        RealPropertyClientDto realPropertyDto = new RealPropertyClientDto();
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData())
                && nonNull(application.getApplicationSellData().getRealProperty())) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            if (operations.contains(VIEW + SALE_OBJECT_INFO)) {
                realPropertyDto.setBuildingDto(new BuildingDto(realProperty.getBuilding()));
                RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
                if (nonNull(metadata)) {
                    realPropertyDto.setFloor(metadata.getFloor());
                    realPropertyDto.setNumberOfRooms(metadata.getNumberOfRooms());
                    realPropertyDto.setTotalArea(metadata.getTotalArea());
                    realPropertyDto.setLivingArea(metadata.getLivingArea());
                    realPropertyDto.setAtelier(metadata.getAtelier());
                    realPropertyDto.setSeparateBathroom(metadata.getSeparateBathroom());
                }
            }
            if (operations.contains(VIEW + SALE_OBJECT_DATA)) {
                realPropertyDto.setApartmentNumber(realProperty.getApartmentNumber());
            }
        } else if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData())
                && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
            if (operations.contains(VIEW + PURCHASE_OBJECT_INFO)) {
                dto.setPurchaseInfoDto(new PurchaseInfoClientDto(application.getApplicationPurchaseData().getPurchaseInfo()));
            }
        }
        dto.setRealPropertyDto(realPropertyDto);
        return dto;
    }

    @Override
    public Long update(String token, Long id, ApplicationClientDTO input) {
        Application application = applicationService.getApplicationById(id);
        return saveApplication(token, application, input);
    }

    @Override
    public Long deleteById(String token, Long id) {
        Application application = applicationService.getApplicationById(id);
        application.setIsRemoved(true);
        return applicationRepository.save(application).getId();
    }

    @Override
    public Long save(String token, ApplicationClientDTO dto) {

        return saveApplication(token, new Application(), dto);
    }


    public Long saveApplication(String token, Application application, ApplicationClientDTO dto) {
        List<String> operations = applicationService.getOperationList(token, application);
        RealPropertyMetadata metadata = null;
        OperationType operationType;
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
        if (operationType.isBuy() && nonNull(dto.getPurchaseInfoDto())) {
            if (operations.contains(UPDATE + PURCHASE_DEAL_INFO)) {
               /* ApplicationPurchaseData purchaseData = entityMappingTool.convertApplicationClientPurchaseData(dto);
                if (operations.contains(UPDATE + PURCHASE_OBJECT_INFO)) {
                    PurchaseInfo info = entityMappingTool.convertClientPurchaseInfo(dto);
                    if (nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
                        info.setId(application.getApplicationPurchaseData().getPurchaseInfo().getId());
                    }
                    purchaseData.setPurchaseInfo(info);
                }
                if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData())) {
                    purchaseData.setId(application.getApplicationPurchaseData().getId());
                }
                purchaseData.setApplication(application);
                application.setApplicationPurchaseData(purchaseData);*/
            }
        } else if (operationType.isSell()) {
            if (nonNull(dto.getSellDataDto()) && operations.contains(UPDATE + SALE_DEAL_INFO)) {
                ApplicationSellDataDto dataDto = dto.getSellDataDto();
                if (isNull(dataDto.getObjectPrice())) {
                    throw BadRequestException.createRequiredIsEmpty("ObjectPrice");
                }
                ApplicationSellData sellData = new ApplicationSellData(dataDto);
                RealPropertyClientDto realPropertyClientDto = dto.getRealPropertyDto();
                if (operations.contains(UPDATE + SALE_OBJECT_DATA) && nonNull(realPropertyClientDto) && nonNull(realPropertyClientDto.getBuildingDto())) {
                    RealProperty realProperty = null;
                    Building building = buildingService.getByPostcode(realPropertyClientDto.getBuildingDto().getPostcode());

                    MetadataStatus approved = entityService.mapEntity(MetadataStatus.class, MetadataStatus.APPROVED);
                    if (nonNull(building)) {
                        realProperty = realPropertyRepository.findByApartmentNumberAndBuildingId(realPropertyClientDto.getApartmentNumber(), building.getId());
                    } else {
                        building = entityMappingTool.convertBuilding(realPropertyClientDto.getBuildingDto());
                    }
                    if (isNull(realProperty)) {
                        realProperty = new RealProperty(realPropertyClientDto, building);
                    }
                    if (operations.contains(UPDATE + SALE_OBJECT_INFO)) {
                        metadata = new RealPropertyMetadata(realPropertyClientDto);
                        if (nonNull(realProperty.getId())) {
                            List<ApplicationSellData> actualSellDataList = realProperty.getActualSellDataList();
                            if (actualSellDataList.size() >= dataProperties.getMaxApplicationCountForOneRealProperty()) {
                                if (isNull(application.getApplicationSellData()) || isNull(application.getApplicationSellData().getRealProperty())
                                        || !application.getApplicationSellData().getRealProperty().getId().equals(realProperty.getId())) {
                                    throw BadRequestException.createMaxApplicationCount(realPropertyClientDto.getApartmentNumber(), building.getPostcode());
                                }
                            }

                        } else {
                            metadata.setMetadataStatus(approved);
                        }
                        metadata.setRealProperty(realProperty);
                        metadata.setApplication(application);
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
        if (nonNull(metadata)) {
            metadataRepository.save(metadata);
        }
        return application.getId();
    }


    @Override
    public List<ApplicationClientDTO> getAllMyAppByOperationTypeId(String clientLogin, String token, Long operationTypeId) {

        List<Application> list = applicationRepository.findAllByOperationType_idAndClientLoginAndIsRemovedFalse(operationTypeId, clientLogin);
        List<ApplicationClientDTO> result = new ArrayList<>();
        for (Application application : list) {
            List<String> operations = applicationService.getOperationList(token, application);
            result.add(mapToApplicationClientDTO(application, operations));
        }

        return result;
    }

}
