package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.web.rest.vm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Service
public class ApplicationManager {
    private final ApplicationRepository applicationRepository;
    private final RealPropertyOwnerRepository realPropertyOwnerRepository;
    private final EntityManager entityManager;
    private final ApplicationConverter applicationConverter;
    private final RealPropertyConverter realPropertyConverter;
    private final RealPropertyOwnerConverter realPropertyOwnerConverter;
    private final DataAccessManager dataAccessManager;
    private static final String[] REAL_PROPERTY_EXCLUDES_FOR_SELL = {
            "id", "cityId",
            "residentialComplexId", "parkingTypeId",
            "street", "streetId",
            "district", "districtId",
            "floorFrom", "floorTo",
            "numberOfRoomsFrom", "numberOfRoomsTo",
            "totalAreaFrom", "totalAreaTo",
            "livingAreaFrom", "livingAreaTo",
            "kitchenAreaFrom", "kitchenAreaTo",
            "balconyAreaFrom", "balconyAreaTo",
            "ceilingHeightFrom", "ceilingHeightTo",
            "numberOfBedroomsFrom", "numberOfBedroomsTo"
    };

    @Autowired
    public ApplicationManager(ApplicationRepository applicationRepository, RealPropertyOwnerRepository realPropertyOwnerRepository, EntityManager entityManager, ApplicationConverter applicationConverter, RealPropertyConverter realPropertyConverter, RealPropertyOwnerConverter realPropertyOwnerConverter, DataAccessManager dataAccessManager) {
        this.applicationRepository = applicationRepository;
        this.realPropertyOwnerRepository = realPropertyOwnerRepository;
        this.entityManager = entityManager;
        this.applicationConverter = applicationConverter;
        this.realPropertyConverter = realPropertyConverter;
        this.realPropertyOwnerConverter = realPropertyOwnerConverter;
        this.dataAccessManager = dataAccessManager;
    }

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public ApplicationDto getById(final String token, Long id) {
        ApplicationDto dto = new ApplicationDto();
        ListResponse<CheckOperationGroupDto> checkOperationList = dataAccessManager.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
        Application application = applicationRepository.getOne(id);
        checkOperationList
                .getData()
                .stream()
                .filter(e -> "APPLICATION_GROUP".equals(e.getCode()))
                .findFirst()
                .ifPresent(e -> {
                    List<String> operations = e.getOperations();
                    for (String oper : operations) {
                        switch (oper) {
                            case "VIEW_SALE_DEAL_INFO":
                                dto.setId(application.getId());
                                dto.setOperationTypeId(application.getOperationType().getId());
                                dto.setObjectTypeId(application.getObjectType().getId());
                                dto.setObjectPrice(application.getObjectPrice());
                                dto.setMortgage(application.getMortgage());
                                dto.setEncumbrance(application.getEncumbrance());
                                dto.setSharedOwnershipProperty(application.getSharedOwnershipProperty());
                                dto.setExchange(application.getExchange());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());
                                dto.setTheSizeOfTrades(application.getTheSizeOfTrades());
                                break;
                            case "NOT_ACCESS_ VIEW_SALE_DEAL_INFO":
                                break;
                            case "VIEW_PURCHASE_DEAL_INFO":
                                dto.setOperationTypeId(application.getOperationType().getId());
                                dto.setObjectTypeId(application.getObjectType().getId());
                                dto.setObjectPriceFrom(application.getObjectPriceFrom());
                                dto.setObjectPriceTo(application.getObjectPriceTo());
                                dto.setMortgage(application.getMortgage());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());
                                break;
                            case "NOT_ACCESS_ VIEW_PURCHASE_DEAL_INFO":
                                break;
                            case "VIEW_DEAL_DATA":
                                dto.setContractPeriod(application.getContractPeriod());
                                dto.setAmount(application.getAmount());
                                dto.setCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice());
                                break;
                        }
                    }
                });
        return dto;
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public void update(Long id, Application var0) {
        applicationRepository.save(var0);
    }

    public Long saveShortFormApplication(ShortFormApplication shortFormApplication) {
        final Application application = new Application();
        if (shortFormApplication.getOperationTypeId() != null) {
            final OperationType operationType = entityManager.getReference(OperationType.class, shortFormApplication.getOperationTypeId());
            application.setOperationType(operationType);
        }
        final RealPropertyOwner client = new RealPropertyOwner();
        client.setPhoneNumber(shortFormApplication.getPhoneNumber());
        client.setFirstName(shortFormApplication.getFirstName());
        client.setSurname(shortFormApplication.getSurname());
        client.setPatronymic(shortFormApplication.getPatronymic());
        application.setRealPropertyOwner(client);
        application.setNote(shortFormApplication.getNote());
        return applicationRepository.save(application).getId();
    }

    public Long sell(final ApplicationDto dto) {
        final Application application = new Application();

        return null;
    }

    public Long buy(final ApplicationDto dto) {
        final Application application = new Application();

        return null;
    }

//    public Long saveApplicationForSell(ApplicationForSell applicationForSell) {
//        final Application application = new Application();
//        if (applicationForSell.getOperationTypeId() != null) {
//            final OperationType reference = entityManager.getReference(OperationType.class, applicationForSell.getOperationTypeId());
//            application.setOperationType(reference);
//        }
//        if (applicationForSell.getObjectTypeId() != null) {
//            final ObjectType reference = entityManager.getReference(ObjectType.class, applicationForSell.getObjectTypeId());
//            application.setObjectType(reference);
//        }
//        /*--------------------------------------------------------------------*/
//        application.setObjectPrice(applicationForSell.getObjectPrice());
//        application.setMortgage(applicationForSell.getMortgage());
//        application.setEncumbrance(applicationForSell.getEncumbrance());
//        application.setSharedOwnershipProperty(applicationForSell.getSharedOwnershipProperty());
//        application.setExchange(applicationForSell.getExchange());
//        application.setProbabilityOfBidding(applicationForSell.getProbabilityOfBidding());
//        application.setTheSizeOfTrades(applicationForSell.getTheSizeOfTrades());
//        application.setContractPeriod(applicationForSell.getContractPeriod());
//        application.setAmount(applicationForSell.getAmount());
//        application.setCommissionIncludedInThePrice(applicationForSell.isCommissionIncludedInThePrice());
//        application.setNote(applicationForSell.getNote());
//        /*--------------------------------------------------------------------*/
//
//        if (applicationForSell.getPossibleReasonForBiddingId() != null) {
//            PossibleReasonForBidding reference = entityManager.getReference(PossibleReasonForBidding.class, applicationForSell.getPossibleReasonForBiddingId());
//            application.setPossibleReasonForBidding(reference);
//        }
//
//        final RealProperty realProperty = new RealProperty();
//        if (applicationForSell.getCityId() != null) {
//            City reference = entityManager.getReference(City.class, applicationForSell.getCityId());
//            realProperty.setCity(reference);
//        }
//        if (applicationForSell.getResidentialComplexId() != null) {
//            ResidentialComplex reference = entityManager.getReference(ResidentialComplex.class, applicationForSell.getResidentialComplexId());
//
//            realProperty.setResidentialComplex(reference);
//        }
//
//        realProperty.setFloor(applicationForSell.getFloor());
//        realProperty.setApartmentNumber(applicationForSell.getApartmentNumber());
//        realProperty.setNumberOfRooms(applicationForSell.getNumberOfRooms());
//        realProperty.setTotalArea(applicationForSell.getTotalArea());
//        realProperty.setLivingArea(applicationForSell.getLivingArea());
//        realProperty.setKitchenArea(applicationForSell.getKitchenArea());
//        realProperty.setBalconyArea(applicationForSell.getBalconyArea());
//        realProperty.setCeilingHeight(applicationForSell.getCeilingHeight());
//        realProperty.setNumberOfBedrooms(applicationForSell.getNumberOfBedrooms());
//        realProperty.setAtelier(applicationForSell.getAtelier());
//        realProperty.setSeparateBathroom(applicationForSell.getSeparateBathroom());
//        if (applicationForSell.getDistrictId() != null) {
//            District reference = entityManager.getReference(District.class, applicationForSell.getDistrictId());
//            realProperty.setDistrict(reference);
//        }
//        realProperty.setNumberOfFloors(applicationForSell.getNumberOfFloors());
//        realProperty.setMaterialOfConstruction(applicationForSell.getMaterialOfConstruction());
//        realProperty.setYearOfConstruction(applicationForSell.getYearOfConstruction());
//        realProperty.setTypeOfElevator(applicationForSell.getTypeOfElevator());
//        realProperty.setConcierge(applicationForSell.getConcierge());
//        realProperty.setWheelchair(applicationForSell.getWheelchair());
//        if (applicationForSell.getParkingId() != null) {
//            ParkingType reference = entityManager.getReference(ParkingType.class, applicationForSell.getParkingId());
//            realProperty.setParkingType(reference);
//        }
//        realProperty.setYardType(applicationForSell.getYardType());
//        realProperty.setPlayground(applicationForSell.getPlayground());
//        application.setRealProperty(realProperty);
//
//        RealPropertyOwner client = new RealPropertyOwner();
//        client.setFirstName(applicationForSell.getFirstName());
//        client.setSurname(applicationForSell.getSurname());
//        client.setPatronymic(applicationForSell.getPatronymic());
//        client.setPhoneNumber(applicationForSell.getPhoneNumber());
//        client.setEmail(applicationForSell.getEmail());
//        client.setGender(applicationForSell.getGender());
//        application.setRealPropertyOwner(client);
//        Application saved = applicationRepository.save(application);
//        return saved.getId();
//    }

//    public Long saveApplicationForBuy(ApplicationForBuy applicationForBuy) {
//        final Application application = new Application();
//        if (applicationForBuy.getOperationTypeId() != null) {
//            final OperationType reference = entityManager.getReference(OperationType.class, applicationForBuy.getOperationTypeId());
//            application.setOperationType(reference);
//        }
//        if (applicationForBuy.getObjectTypeId() != null) {
//            final ObjectType reference = entityManager.getReference(ObjectType.class, applicationForBuy.getObjectTypeId());
//            application.setObjectType(reference);
//        }
//        application.setObjectPriceFrom(applicationForBuy.getObjectPriceFrom());
//        application.setObjectPriceTo(applicationForBuy.getObjectPriceTo());
//        application.setMortgage(applicationForBuy.getMortgage());
//        application.setProbabilityOfBidding(applicationForBuy.getProbabilityOfBidding());
//        application.setTheSizeOfTrades(applicationForBuy.getTheSizeOfTrades());
//        if (applicationForBuy.getPossibleReasonForBiddingId() != null) {
//            PossibleReasonForBidding reference = entityManager.getReference(PossibleReasonForBidding.class, applicationForBuy.getPossibleReasonForBiddingId());
//            application.setPossibleReasonForBidding(reference);
//        }
//        application.setContractPeriod(applicationForBuy.getContractPeriod());
//        application.setAmount(applicationForBuy.getAmount());
//        application.setCommissionIncludedInThePrice(applicationForBuy.isCommissionIncludedInThePrice());
//        application.setNote(applicationForBuy.getNote());
//
//        RealProperty realProperty = new RealProperty();
////        realProperty.setFloorFrom(applicationForBuy.getFloorFrom());
////        realProperty.setFloorTo(applicationForBuy.getFloorTo());
////        realProperty.setNumberOfRoomsFrom(applicationForBuy.getNumberOfRoomsFrom());
////        realProperty.setNumberOfRoomsTo(applicationForBuy.getNumberOfRoomsTo());
////        realProperty.setTotalAreaFrom(applicationForBuy.getTotalAreaFrom());
////        realProperty.setTotalAreaTo(applicationForBuy.getTotalAreaTo());
////        realProperty.setLivingAreaFrom(applicationForBuy.getLivingAreaFrom());
////        realProperty.setLivingAreaTo(applicationForBuy.getLivingAreaTo());
////        realProperty.setKitchenAreaFrom(applicationForBuy.getKitchenAreaFrom());
////        realProperty.setKitchenAreaTo(applicationForBuy.getKitchenAreaTo());
////        realProperty.setBalconyAreaFrom(applicationForBuy.getBalconyAreaFrom());
////        realProperty.setBalconyAreaTo(applicationForBuy.getBalconyAreaTo());
////        realProperty.setCeilingHeightFrom(applicationForBuy.getCeilingHeightFrom());
////        realProperty.setCeilingHeightTo(applicationForBuy.getCeilingHeightTo());
////        realProperty.setNumberOfBedroomsFrom(applicationForBuy.getNumberOfBedroomsFrom());
////        realProperty.setNumberOfBedroomsTo(applicationForBuy.getNumberOfBedroomsTo());
//        realProperty.setAtelier(applicationForBuy.getAtelier());
//        realProperty.setSeparateBathroom(applicationForBuy.getSeparateBathroom());
//        if (applicationForBuy.getDistrictId() != null) {
//            District reference = entityManager.getReference(District.class, applicationForBuy.getDistrictId());
//            realProperty.setDistrict(reference);
//        }
//        realProperty.setNumberOfFloors(applicationForBuy.getNumberOfFloors());
//        realProperty.setMaterialOfConstruction(applicationForBuy.getMaterialOfConstruction());
//        realProperty.setYearOfConstruction(applicationForBuy.getYearOfConstruction());
//        realProperty.setTypeOfElevator(applicationForBuy.getTypeOfElevator());
//        realProperty.setConcierge(applicationForBuy.getConcierge());
//        realProperty.setWheelchair(applicationForBuy.getWheelchair());
//        if (applicationForBuy.getParkingId() != null) {
//            ParkingType reference = entityManager.getReference(ParkingType.class, applicationForBuy.getParkingId());
//            realProperty.setParkingType(reference);
//        }
//        realProperty.setYardType(applicationForBuy.getYardType());
//        realProperty.setPlayground(applicationForBuy.getPlayground());
//        realProperty.setApartmentsOnTheSite(applicationForBuy.getApartmentsOnTheSite());
//        application.setRealProperty(realProperty);
//
//        RealPropertyOwner client = new RealPropertyOwner();
//        client.setFirstName(applicationForBuy.getFirstName());
//        client.setSurname(applicationForBuy.getSurname());
//        client.setPatronymic(applicationForBuy.getPatronymic());
//        client.setPhoneNumber(applicationForBuy.getPhoneNumber());
//        client.setEmail(applicationForBuy.getEmail());
//        client.setGender(applicationForBuy.getGender());
//        application.setRealPropertyOwner(client);
//        Application saved = applicationRepository.save(application);
//        return saved.getId();
//    }

    private Long saveShortFormApplication(final ApplicationDto dto) {
        final Application application = new Application();
        final RealPropertyOwner client;
        if (dto.getClientId() != null) {
            client = realPropertyOwnerRepository.getOne(dto.getClientId());
        } else {
            client = new RealPropertyOwner();
            client.setPhoneNumber(dto.getPhoneNumber());
            client.setFirstName(dto.getFirstName());
            client.setSurname(dto.getSurname());
            client.setPatronymic(dto.getPatronymic());
            realPropertyOwnerRepository.save(client);
        }
        application.setRealPropertyOwner(client);
        application.setNote(dto.getNote());
        if (dto.getOperationTypeId() != null) {
            final OperationType operationType = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
            application.setOperationType(operationType);
        }
        Long id = applicationRepository.save(application).getId();
        return id;
    }

    //todo transactional
    public Long saveApplication(final String token, final ApplicationType applicationType, final ApplicationDto dto) {
        Long id = null;
        final Application application;
        final RealPropertyOwner client;
        final RealProperty realProperty;
        switch (applicationType) {
            case SHORT_FORM:
                id = saveShortFormApplication(dto);
                break;
            case BUY:
                application = applicationConverter.toBuy(dto);
                realProperty = realPropertyConverter.toBuy(dto);
                client = realPropertyOwnerConverter.toSell(dto);
                application.setRealProperty(realProperty);
                application.setRealPropertyOwner(client);
                id = applicationRepository.save(application).getId();
                break;
            case SELL:
                application = applicationConverter.toSell(dto);
                realProperty = realPropertyConverter.toSell(dto);
                client = realPropertyOwnerConverter.toSell(dto);
                application.setRealProperty(realProperty);
                application.setRealPropertyOwner(client);
                id = applicationRepository.save(application).getId();
                break;
            default:
                break;
        }
        return id;
    }

    public List<RecentlyCreatedApplication> getRecentlyCreatedApps(final String token) {
        applicationRepository.getRecentlyCreatedApps();
        return null;
    }
}
