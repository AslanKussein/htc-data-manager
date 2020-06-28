package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationSource;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;
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

        } else if (operationType.isSell()) {
            // throw new Exception("Для автосоздания заявки тип должен быть Покупка");
            /*if (nonNull(dto.getSellDataDto()) && operations.contains(UPDATE + SALE_DEAL_INFO)) {
                ApplicationSellDataDto dataDto = dto.getSellDataDto();
                if (isNull(dataDto.getObjectPrice())) {
                    throw BadRequestException.createRequiredIsEmpty("ObjectPrice");
                }
                ApplicationSellData sellData = new ApplicationSellData(dataDto);
                RealPropertyDto realPropertyDto = dto.getRealPropertyDto();
                if (operations.contains(UPDATE + SALE_OBJECT_DATA) && nonNull(realPropertyDto) && nonNull(realPropertyDto.getBuildingDto())) {
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
                    if (operations.contains(UPDATE + SALE_OBJECT_INFO)) {
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
                            } else {
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
//                        realProperty.getMetadataList().add(metadata);
//                        realProperty.getFileList().add(realPropertyFile);
                    }
                    sellData.setRealProperty(realProperty);
                }
                sellData.setApplication(application);
                if (nonNull(application.getId()) && nonNull(application.getApplicationSellData())) {
                    sellData.setId(application.getApplicationSellData().getId());
                }
                application.setApplicationSellData(sellData);
            }
       */
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
}
