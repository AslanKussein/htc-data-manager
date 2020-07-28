package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.SecurityException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyMetadataRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.repository.filter.ApplicationSpecifications;
import kz.dilau.htcdatamanager.service.*;
import kz.dilau.htcdatamanager.util.EntityMappingTool;
import kz.dilau.htcdatamanager.web.dto.ProfileClientDto;
import kz.dilau.htcdatamanager.web.dto.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationClientServiceImpl implements ApplicationClientService {


    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final BuildingService buildingService;
    private final RealPropertyRepository realPropertyRepository;
    private final DataProperties dataProperties;
    private final EntityMappingTool entityMappingTool;
    private final RealPropertyMetadataRepository metadataRepository;
    private final ApplicationService applicationService;
    private final KeycloakService keycloakService;


    @Override
    public ApplicationClientDTO getById(final String token, Long id) {
        Application application = applicationService.getApplicationById(id);
        return new ApplicationClientDTO(application);
    }

    @Override
    public Long update(String token, Long id, ApplicationClientDTO input) {
        Application application = applicationService.getApplicationById(id);
        input.setClientLogin(getAuthorName());
        return saveApplication(application, input);
    }

    @Override
    public Long deleteById(String token, Long id) {
        Application application = applicationService.getApplicationById(id);
        application.setIsRemoved(true);
        return applicationRepository.save(application).getId();
    }

    @Override
    public Long save(String token, ApplicationClientDTO dto) {
        dto.setClientLogin(getAuthorName());
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

    private Long saveApplication(Application application, ApplicationClientDTO dto) {
        RealPropertyMetadata metadata = null;
        OperationType operationType;
        ApplicationSource applicationSource;
        if (nonNull(application.getId())) {
            operationType = application.getOperationType();
        } else {
            operationType = entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId());

            application.setClientLogin(dto.getClientLogin());
            if (nonNull(dto.getDeviceUuid())) application.setDeviceUuid(dto.getDeviceUuid());
            application.setOperationType(operationType);

            ApplicationStatus status = entityService.mapRequiredEntity(ApplicationStatus.class, ApplicationStatus.FIRST_CONTACT);
            application.setApplicationStatus(status);
            application.getStatusHistoryList().add(ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(status)
                    .build());

            application.setObjectType(entityService.mapRequiredEntity(ObjectType.class, dto.getObjectTypeId()));
        }
        if (operationType.isBuy() && nonNull(dto.getPurchaseInfoClientDto())) {
            PurchaseInfoClientDto purchaseData = dto.getPurchaseInfoClientDto();
            PurchaseInfo info = entityMappingTool.convertClientPurchaseInfo(dto);
            if (nonNull(application.getApplicationPurchaseData())
                    && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
                info.setId(application.getApplicationPurchaseData().getPurchaseInfo().getId());
            }
            if (nonNull(application.getId()) && nonNull(application.getApplicationPurchaseData())) {
                purchaseData.setDataId(application.getApplicationPurchaseData().getId());
            }
            application.setApplicationPurchaseData(new ApplicationPurchaseData(application, purchaseData, info,
                    entityService.mapRequiredEntity(City.class, purchaseData.getCityId()),
                    entityService.mapRequiredEntity(District.class, purchaseData.getDistrictId()) ));

        } else if (operationType.isSell()) {
            if (nonNull(dto.getSellDataClientDto())) {
                ApplicationSellDataClientDto dataDto = dto.getSellDataClientDto();
                if (isNull(dataDto.getObjectPrice())) {
                    throw BadRequestException.createRequiredIsEmpty("ObjectPrice");
                }
                ApplicationSellData sellData = new ApplicationSellData(dataDto, null, null);
                RealPropertyClientDto realPropertyClientDto = dto.getSellDataClientDto().getRealPropertyClientDto();
                if (nonNull(realPropertyClientDto) && nonNull(realPropertyClientDto.getBuildingDto())) {
                    RealProperty realProperty = null;

                    if (isNull(realPropertyClientDto.getBuildingDto().getPostcode())) {
                        throw BadRequestException.createRequiredIsEmpty("postcode");
                    }
                    Building building = buildingService.getByPostcode(realPropertyClientDto.getBuildingDto().getPostcode());

                    MetadataStatus approved = entityService.mapEntity(MetadataStatus.class, MetadataStatus.APPROVED);
                    if (nonNull(building)) {
                        realProperty = realPropertyRepository.findByApartmentNumberAndBuildingId(realPropertyClientDto.getApartmentNumber(), building.getId());
                    } else {
                        building = entityMappingTool.convertBuilding(realPropertyClientDto.getBuildingDto());
                    }
                    if (isNull(realProperty)) {
                        realProperty = new RealProperty(realPropertyClientDto, building, null);
                    }
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
                    metadata.setApplication(application);
                    // }
                    realProperty = new RealProperty(realPropertyClientDto, building, metadata);
                    sellData = new ApplicationSellData(dataDto, building, metadata);

                    sellData.setRealProperty(realProperty);
                    metadata.setRealProperty(realProperty);
                }
                sellData.setApplication(application);
                if (nonNull(application.getId()) && nonNull(application.getApplicationSellData())) {
                    sellData.setId(application.getApplicationSellData().getId());
                }
                application.setApplicationSellData(sellData);
            }
        }
        applicationSource = entityService.mapRequiredEntity(ApplicationSource.class, ApplicationSource.CA);
        application.setApplicationSource(applicationSource);
        application = applicationRepository.save(application);
        if (nonNull(metadata)) {
            metadataRepository.save(metadata);
        }
        return application.getId();
    }


    @Override
    public List<ApplicationClientDTO> getAllMyAppByOperationTypeId(String clientLogin, String token, Long operationTypeId) {

        Specification<Application> specification = ApplicationSpecifications
                .isRemovedEquals(false)
                .and(ApplicationSpecifications.clientLoginEquals(clientLogin)
                        .and(ApplicationSpecifications.operationTypeIdEquals(operationTypeId)
                                .and(ApplicationSpecifications.applicationStatusCodeNotEquals("002010"))));

        List<Application> list = applicationRepository.findAll(specification);
        return list.stream().map(ApplicationClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long save(ClientApplicationCreateDTO dto) {
        if (isNull(dto.getApplication())) throw BadRequestException.createRequiredIsEmpty("application");
        if (isNull(dto.getClientName())) throw  BadRequestException.createRequiredIsEmpty("clientName");
        if (isNull(dto.getDeviceUuid())) throw BadRequestException.createRequiredIsEmpty("deviceUuid");
        if (isNull(dto.getPhoneNumber())) throw BadRequestException.createRequiredIsEmpty("phoneNumber");

        List<String> logins = new ArrayList<>();
        logins.add(dto.getPhoneNumber());
        List<ProfileClientDto> profileList = keycloakService.readClientInfoByLogins(logins);

        if (profileList.isEmpty() || isNull(profileList.get(0)) || isNull(profileList.get(0).getPhoneNumber())) {
            ProfileClientDto newClient  = new ProfileClientDto();
            newClient.setFirstName(dto.getClientName());
            newClient.setPhoneNumber(dto.getPhoneNumber());

            try {
                keycloakService.saveClient(newClient);
            } catch (Exception e) {
                e.printStackTrace();
                throw BadRequestException.createUserError();
            }
        }

        List<ClientDeviceDto> deviceDto = keycloakService.getDevices(null, dto.getDeviceUuid());

        if (deviceDto.isEmpty() || isNull(deviceDto.get(0))) {
            throw BadRequestException.deviceNotFound(dto.getDeviceUuid());
        }

        if (nonNull(deviceDto.get(0).getClientLogin())) {
            throw BadRequestException.authoriationRequired(deviceDto.get(0).getClientLogin());
        }

        dto.getApplication().setClientLogin(dto.getPhoneNumber());
        dto.getApplication().setDeviceUuid(dto.getDeviceUuid());

        Application app;

        if (nonNull(dto.getApplication().getId())) {
            app = applicationService.getApplicationById(dto.getApplication().getId());
            if (!app.getClientLogin().equals(dto.getPhoneNumber())) {
                throw SecurityException.createPermissionNotFound();
            }
        } else {
            app = new Application();
        }
        saveApplication(app, dto.getApplication());

        return app.getId();
    }
}
