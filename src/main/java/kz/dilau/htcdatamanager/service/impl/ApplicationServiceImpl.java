package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ParkingTypeRepository;
import kz.dilau.htcdatamanager.repository.dictionary.PossibleReasonForBiddingRepository;
import kz.dilau.htcdatamanager.repository.dictionary.TypeOfElevatorRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.RealPropertyService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final ParkingTypeRepository parkingTypeRepository;
    private final PossibleReasonForBiddingRepository reasonForBiddingRepository;
    private final RealPropertyService realPropertyService;
    private final ClientService clientService;
    private final TypeOfElevatorRepository typeOfElevatorRepository;

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
        return ApplicationDto.builder()
                .id(application.getId())
                .clientDto(mapToClientDto(application.getClient()))
                .realPropertyRequestDto(nonNull(application.getRealProperty()) ? realPropertyService.mapToRealPropertyDto(application.getRealProperty()) : null)
                .operationTypeId(application.getOperationType().getId())
                .objectPrice(application.getObjectPrice())
                .mortgage(application.getMortgage())
                .encumbrance(application.getEncumbrance())
                .sharedOwnershipProperty(application.getSharedOwnershipProperty())
                .exchange(application.getExchange())
                .probabilityOfBidding(application.getProbabilityOfBidding())
                .theSizeOfTrades(application.getTheSizeOfTrades())
                .possibleReasonForBiddingIdList(application.getPossibleReasonsForBidding().stream()
                        .map(PossibleReasonForBidding::getId)
                        .collect(Collectors.toList()))
                .contractPeriod(application.getContractPeriod())
                .contractNumber(application.getContractNumber())
                .amount(application.getAmount())
                .isCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice())
                .note(application.getNote())
                .agent(application.getCurrentAgent())
//                .statusHistoryDtoList(mapStatusHistoryList(application))
                .build();
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

    private ClientDto mapToClientDto(Client client) {
        return new ClientDto(client);
    }

    @Override
    @Transactional
    public Long save(ApplicationDto dto) {
        return saveApplication(new Application(), dto);
    }

    @Override
    public Long saveLightApplication(ApplicationLightDto dto) {
        Client client = getClient(dto.getClientDto());
        String agent = getAppointmentAgent(dto.getAgent());
        Application application = Application.builder()
                .client(client)
                .operationType(entityService.mapRequiredEntity(OperationType.class, dto.getOperationTypeId()))
                .note(dto.getNote())
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
        Client client = getClient(dto.getClientDto());
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
        }
        RealPropertyRequestDto realPropertyRequestDto = dto.getRealPropertyRequestDto();
        RealProperty realProperty = RealProperty.builder()
                .objectType(entityService.mapEntity(ObjectType.class, realPropertyRequestDto.getObjectTypeId()))
                .cadastralNumber(realPropertyRequestDto.getCadastralNumber())
                .floor(realPropertyRequestDto.getFloor())
                .apartmentNumber(realPropertyRequestDto.getApartmentNumber())
                .numberOfRooms(realPropertyRequestDto.getNumberOfRooms())
                .totalArea(realPropertyRequestDto.getTotalArea())
                .livingArea(realPropertyRequestDto.getLivingArea())
                .kitchenArea(realPropertyRequestDto.getKitchenArea())
                .balconyArea(realPropertyRequestDto.getBalconyArea())
                .numberOfBedrooms(realPropertyRequestDto.getNumberOfBedrooms())
                .atelier(realPropertyRequestDto.getAtelier())
                .separateBathroom(realPropertyRequestDto.getSeparateBathroom())
                .landArea(realPropertyRequestDto.getLandArea())
                .sewerage(entityService.mapEntity(Sewerage.class, realPropertyRequestDto.getSewerageId()))
                .heatingSystem(entityService.mapEntity(HeatingSystem.class, realPropertyRequestDto.getHeatingSystemId()))
                .residentialComplex(entityService.mapEntity(ResidentialComplex.class, realPropertyRequestDto.getResidentialComplexId()))
                .generalCharacteristics(null)
                .latitude(realPropertyRequestDto.getLatitude)
                .longitude(realPropertyRequestDto.getLongitude)
                .build();

        if (isNull(realProperty.getResidentialComplexId())) {
            GeneralCharacteristics generalCharacteristics = GeneralCharacteristics.builder()
                    .houseNumber(realPropertyRequestDto.getHouseNumber())
                    .houseNumberFraction(realPropertyRequestDto.getHouseNumberFraction())
                    .ceilingHeight(realPropertyRequestDto.getCeilingHeight())
                    .housingClass(realPropertyRequestDto.getHousingClass())
                    .housingCondition(realPropertyRequestDto.getHousingCondition())
                    .yearOfConstruction(realPropertyRequestDto.getYearOfConstruction())
                    .numberOfFloors(realPropertyRequestDto.getNumberOfFloors())
                    .numberOfApartments(realPropertyRequestDto.getNumberOfApartments())
                    .apartmentsOnTheSite(realPropertyRequestDto.getApartmentsOnTheSite())
                    .concierge(realPropertyRequestDto.getConcierge())
                    .wheelchair(realPropertyRequestDto.getWheelchair())
                    .playground(realPropertyRequestDto.getPlayground())
                    .materialOfConstruction(entityService.mapEntity(MaterialOfConstruction.class, realPropertyRequestDto.getMaterialOfConstructionId()))
                    .city(entityService.mapRequiredEntity(City.class, realPropertyRequestDto.getCityId()))
                    .district(entityService.mapEntity(District.class, realPropertyRequestDto.getDistrictId()))
                    .propertyDeveloper(entityService.mapEntity(PropertyDeveloper.class, realPropertyRequestDto.getPropertyDeveloperId()))
                    .street(entityService.mapEntity(Street.class, realPropertyRequestDto.getStreetId()))
                    .yardType(entityService.mapEntity(YardType.class, realPropertyRequestDto.getYardTypeId()))
                    .build();

            if (nonNull(realPropertyRequestDto.getParkingTypeIds()) && !realPropertyRequestDto.getParkingTypeIds().isEmpty()) {
                generalCharacteristics.getParkingTypes().clear();
                generalCharacteristics.getParkingTypes().addAll(parkingTypeRepository.findByIdIn(realPropertyRequestDto.getParkingTypeIds()));
            }
            if (nonNull(realPropertyRequestDto.getTypeOfElevatorList()) && !realPropertyRequestDto.getTypeOfElevatorList().isEmpty()) {
                generalCharacteristics.getTypesOfElevator().clear();
                generalCharacteristics.getTypesOfElevator().addAll(typeOfElevatorRepository.findByIdIn(realPropertyRequestDto.getTypeOfElevatorList()));
            }
            realProperty.setGeneralCharacteristics(generalCharacteristics);
        }
        if (operationType.getCode().equals(OperationType.BUY) && nonNull(realPropertyRequestDto.getPurchaseInfoDto())) {
            PurchaseInfoDto infoDto = realPropertyRequestDto.getPurchaseInfoDto();
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setObjectPrice(infoDto.getObjectPricePeriod());
            purchaseInfo.setFloor(infoDto.getFloorPeriod());
            purchaseInfo.setNumberOfRooms(infoDto.getNumberOfRoomsPeriod());
            purchaseInfo.setNumberOfBedrooms(infoDto.getNumberOfBedroomsPeriod());
            purchaseInfo.setNumberOfFloors(infoDto.getNumberOfFloorsPeriod());
            purchaseInfo.setTotalArea(infoDto.getTotalAreaPeriod());
            purchaseInfo.setLivingArea(infoDto.getLivingAreaPeriod());
            purchaseInfo.setKitchenArea(infoDto.getKitchenAreaPeriod());
            purchaseInfo.setLandArea(infoDto.getLandAreaPeriod());
            purchaseInfo.setBalconyArea(infoDto.getBalconyAreaPeriod());
            purchaseInfo.setCeilingHeight(infoDto.getCeilingHeightPeriod());
            purchaseInfo.setRealProperty(realProperty);

            realProperty.setPurchaseInfo(purchaseInfo);
        }
        if (operationType.getCode().equals(OperationType.SELL)) {
            if (!CollectionUtils.isEmpty(realPropertyRequestDto.getHousingPlanImageIdList())) {
                realProperty.getFilesMap().put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(realPropertyRequestDto.getHousingPlanImageIdList()));
            }
            if (!CollectionUtils.isEmpty(realPropertyRequestDto.getPhotoIdList())) {
                realProperty.getFilesMap().put(RealPropertyFileType.PHOTO, new HashSet<>(realPropertyRequestDto.getPhotoIdList()));
            }
            if (!CollectionUtils.isEmpty(realPropertyRequestDto.getVirtualTourImageIdList())) {
                realProperty.getFilesMap().put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(realPropertyRequestDto.getVirtualTourImageIdList()));
            }
        }
        if (nonNull(application.getId())) {
            realProperty.setId(application.getRealProperty().getId());
            if (nonNull(application.getRealProperty().getPurchaseInfo()) && nonNull(realProperty.getPurchaseInfo())) {
                realProperty.getPurchaseInfo().setId(application.getRealProperty().getPurchaseInfo().getId());
            }
            if (nonNull(application.getRealProperty().getGeneralCharacteristicsId()) && nonNull(realProperty.getGeneralCharacteristics())) {
                realProperty.getGeneralCharacteristics().setId(application.getRealProperty().getGeneralCharacteristicsId());
            }
        } else {
            ApplicationStatus status = applicationStatusRepository.getOne(ApplicationStatus.FIRST_CONTACT);
            application.setApplicationStatus(status);
            ApplicationStatusHistory statusHistory = ApplicationStatusHistory.builder()
                    .application(application)
                    .applicationStatus(status)
                    .build();
            application.getStatusHistoryList().add(statusHistory);
        }
        application.setRealProperty(realProperty);
        application.setClient(client);
        application.setOperationType(operationType);
        application.setNote(dto.getNote());
        application.setObjectPrice(dto.getObjectPrice());
        application.setMortgage(dto.getMortgage());
        application.setEncumbrance(dto.getEncumbrance());
        application.setSharedOwnershipProperty(dto.getSharedOwnershipProperty());
        application.setExchange(dto.getExchange());
        application.setProbabilityOfBidding(dto.getProbabilityOfBidding());
        application.setTheSizeOfTrades(dto.getTheSizeOfTrades());
        application.setContractPeriod(dto.getContractPeriod());
        application.setContractNumber(dto.getContractNumber());
        application.setAmount(dto.getAmount());
        application.setCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice());
        if (nonNull(dto.getPossibleReasonForBiddingIdList()) && !dto.getPossibleReasonForBiddingIdList().isEmpty()) {
            application.getPossibleReasonsForBidding().clear();
            application.getPossibleReasonsForBidding().addAll(reasonForBiddingRepository.findByIdIn(dto.getPossibleReasonForBiddingIdList()));
        }
        return applicationRepository.save(application).getId();
    }

    private Client getClient(ClientDto dto) {
        Client client;
        if (isNull(dto.getId()) || dto.getId() == 0L) {
            ClientDto clientFromBd = clientService.findClientByPhoneNumber(dto.getPhoneNumber());
            if (nonNull(clientFromBd)) {
                throw BadRequestException.createClientHasFounded(dto.getPhoneNumber());
            }
            client = Client.builder()
                    .firstName(dto.getFirstName())
                    .surname(dto.getSurname())
                    .patronymic(dto.getPatronymic())
                    .phoneNumber(dto.getPhoneNumber())
                    .email(dto.getEmail())
                    .gender(dto.getGender())
                    .build();
        } else {
            client = clientService.getClientById(dto.getId());
        }
        return client;
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

    private Application getApplicationById(Long id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            if (optionalApplication.get().getIsRemoved()) {
                throw EntityRemovedException.createApplicationRemovedById(id);
            }
            return optionalApplication.get();
        } else {
            throw NotFoundException.createApplicationNotFoundById(id);
        }
    }

    @Override
    public Long changeStatus(ChangeStatusDto dto) {
        Application application = getApplicationById(dto.getApplicationId());
        if (dto.getStatusId().equals(ApplicationStatus.DEMO)) {
            if (application.getApplicationStatus().getId().equals(ApplicationStatus.CONTRACT) || application.getApplicationStatus().getId().equals(ApplicationStatus.PHOTO_SHOOT) || application.getApplicationStatus().getId().equals(ApplicationStatus.ADS)) {
                application.setApplicationStatus(entityService.mapRequiredEntity(ApplicationStatus.class, dto.getStatusId()));
                return applicationRepository.save(application).getId();
            } else {
                throw BadRequestException.createChangeStatus(application.getApplicationStatus().getMultiLang().getNameRu());
            }
        } else {
            throw BadRequestException.createChangeStatus(application.getApplicationStatus().getMultiLang().getNameRu());
        }
    }
}
