package kz.dilau.htcdatamanager.component.application;

import kz.dilau.htcdatamanager.component.dataaccess.DataAccessManager;
import kz.dilau.htcdatamanager.component.owner.RealPropertyOwnerDto;
import kz.dilau.htcdatamanager.component.property.PurchaseInfoDto;
import kz.dilau.htcdatamanager.component.property.RealPropertyRequestDto;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.component.utils.PeriodUtils.mapToBigDecimalPeriod;
import static kz.dilau.htcdatamanager.component.utils.PeriodUtils.mapToIntegerPeriod;

@RequiredArgsConstructor
@Service
public class ApplicationManagerImpl implements ApplicationManager {
    private final ApplicationRepository applicationRepository;
    private final RealPropertyOwnerRepository ownerRepository;
    private final EntityManager entityManager;
    private final ApplicationStatusRepository applicationStatusRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final GeneralCharacteristicsRepository generalCharacteristicsRepository;
    private final RealPropertyRepository realPropertyRepository;
    private final DataAccessManager dataAccessManager;


    @Override
    public ApplicationDto getById(final String token, Long id) {
//        ApplicationDto dto = new ApplicationDto();
//        ListResponse<CheckOperationGroupDto> checkOperationList = dataAccessManager.getCheckOperationList(token, Arrays.asList("APPLICATION_GROUP", "REAL_PROPERTY_GROUP", "CLIENT_GROUP"));
        Application application = applicationRepository.getOne(id);
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
        return mapToApplicationDto(application);
    }

    private ApplicationDto mapToApplicationDto(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .ownerDto(mapToOwnerDto(application.getOwner()))
                .realPropertyRequestDto(mapToRealPropertyDto(application.getRealProperty()))
                .operationTypeId(application.getOperationType().getId())
                .objectPrice(application.getObjectPrice())
                .mortgage(application.getMortgage())
                .encumbrance(application.getEncumbrance())
                .sharedOwnershipProperty(application.getSharedOwnershipProperty())
                .exchange(application.getExchange())
                .probabilityOfBidding(application.getProbabilityOfBidding())
                .theSizeOfTrades(application.getTheSizeOfTrades())
                .contractPeriod(application.getContractPeriod())
                .amount(application.getAmount())
                .isCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice())
                .note(application.getNote())
                .build();
    }

    private RealPropertyOwnerDto mapToOwnerDto(RealPropertyOwner owner) {
        return new RealPropertyOwnerDto(owner);
    }

    private RealPropertyRequestDto mapToRealPropertyDto(RealProperty realProperty) {
        GeneralCharacteristics generalCharacteristics = nonNull(realProperty.getResidentialComplex()) ? realProperty.getResidentialComplex().getGeneralCharacteristics() : realProperty.getGeneralCharacteristics();
        return RealPropertyRequestDto.builder()
                .objectTypeId(realProperty.getObjectTypeId())
                .cityId(generalCharacteristics.getCityId())
                .districtId(generalCharacteristics.getDistrictId())
                .streetId(generalCharacteristics.getStreetId())
                .houseNumber(generalCharacteristics.getHouseNumber())
                .houseNumberFraction(generalCharacteristics.getHouseNumberFraction())
                .floor(realProperty.getFloor())
                .residentialComplexId(realProperty.getResidentialComplexId())
                .cadastralNumber(realProperty.getCadastralNumber())
                .apartmentNumber(realProperty.getApartmentNumber())
                .numberOfRooms(realProperty.getNumberOfRooms())
                .totalArea(realProperty.getTotalArea())
                .livingArea(realProperty.getLivingArea())
                .kitchenArea(realProperty.getKitchenArea())
                .balconyArea(realProperty.getBalconyArea())
                .ceilingHeight(generalCharacteristics.getCeilingHeight())
                .numberOfBedrooms(realProperty.getNumberOfBedrooms())
                .atelier(realProperty.getAtelier())
                .separateBathroom(realProperty.getSeparateBathroom())
                .numberOfFloors(generalCharacteristics.getNumberOfFloors())
                .apartmentsOnTheSite(realProperty.getApartmentNumber())
                .materialOfConstructionId(generalCharacteristics.getMaterialOfConstructionId())
                .yearOfConstruction(generalCharacteristics.getYearOfConstruction())
                .concierge(generalCharacteristics.getConcierge())
                .wheelchair(generalCharacteristics.getWheelchair())
                .yardTypeId(generalCharacteristics.getYardTypeId())
                .playground(generalCharacteristics.getPlayground())
                .parkingTypeId(generalCharacteristics.getParkingTypeId())
                .propertyDeveloperId(generalCharacteristics.getPropertyDeveloperId())
                .housingClass(generalCharacteristics.getHousingClass())
                .housingCondition(generalCharacteristics.getHousingCondition())
                .sewerageId(realProperty.getSewerageId())
                .heatingSystemId(realProperty.getHeatingSystemId())
                .numberOfApartments(generalCharacteristics.getNumberOfApartments())
                .landArea(realProperty.getLandArea())
                .purchaseInfoDto(mapToPurchaseInfoDto(realProperty.getPurchaseInfo()))
                .build();
    }

    private PurchaseInfoDto mapToPurchaseInfoDto(PurchaseInfo info) {
        if (nonNull(info)) {
            return PurchaseInfoDto.builder()
                    .objectPricePeriod(mapToBigDecimalPeriod(info.getObjectPriceFrom(), info.getObjectPriceTo()))
                    .floorPeriod(mapToIntegerPeriod(info.getFloorFrom(), info.getFloorTo()))
                    .numberOfRoomsPeriod(mapToIntegerPeriod(info.getNumberOfRoomsFrom(), info.getNumberOfRoomsTo()))
                    .numberOfBedroomsPeriod(mapToIntegerPeriod(info.getNumberOfBedroomsFrom(), info.getNumberOfBedroomsTo()))
                    .numberOfFloorsPeriod(mapToIntegerPeriod(info.getNumberOfFloorsFrom(), info.getNumberOfFloorsTo()))
                    .totalAreaPeriod(mapToBigDecimalPeriod(info.getTotalAreaFrom(), info.getTotalAreaTo()))
                    .livingAreaPeriod(mapToBigDecimalPeriod(info.getLivingAreaFrom(), info.getLivingAreaTo()))
                    .kitchenAreaPeriod(mapToBigDecimalPeriod(info.getKitchenAreaFrom(), info.getKitchenAreaTo()))
                    .balconyAreaPeriod(mapToBigDecimalPeriod(info.getBalconyAreaFrom(), info.getBalconyAreaTo()))
                    .landAreaPeriod(mapToBigDecimalPeriod(info.getLandAreaFrom(), info.getLandAreaTo()))
                    .ceilingHeightPeriod(mapToBigDecimalPeriod(info.getCeilingHeightFrom(), info.getCeilingHeightTo()))
                    .build();
        }
        return null;
    }

    @Override
    public List<ApplicationDto> getAll(String token) {
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public Long save(String token, ApplicationDto dto) {
        RealPropertyOwner owner = getOwner(dto.getOwnerDto());
        OperationType operationType = entityManager.getReference(OperationType.class, dto.getOperationTypeId());
        RealPropertyRequestDto realPropertyRequestDto = dto.getRealPropertyRequestDto();
        RealProperty realProperty = RealProperty.builder()
                .objectTypeId(realPropertyRequestDto.getObjectTypeId())
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
                .sewerageId(realPropertyRequestDto.getSewerageId())
                .heatingSystemId(realPropertyRequestDto.getHeatingSystemId())
                .residentialComplexId(realPropertyRequestDto.getResidentialComplexId())
                .build();

        if (isNull(realProperty.getResidentialComplex())) {
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
                    .cityId(realPropertyRequestDto.getCityId())
                    .districtId(realPropertyRequestDto.getDistrictId())
                    .streetId(realPropertyRequestDto.getStreetId())
                    .propertyDeveloperId(realPropertyRequestDto.getPropertyDeveloperId())
                    .materialOfConstructionId(realPropertyRequestDto.getMaterialOfConstructionId())
                    .parkingTypeId(realPropertyRequestDto.getParkingTypeId())
                    .yardTypeId(realPropertyRequestDto.getYardTypeId())
                    .build();
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

            realProperty.setPurchaseInfo(purchaseInfo);
        }
        if (!CollectionUtils.isEmpty(realPropertyRequestDto.getHousingPlanImageIdList())) {
            realProperty.getFilesMap().put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(realPropertyRequestDto.getHousingPlanImageIdList()));
        }
        if (!CollectionUtils.isEmpty(realPropertyRequestDto.getPhotoIdList())) {
            realProperty.getFilesMap().put(RealPropertyFileType.PHOTO, new HashSet<>(realPropertyRequestDto.getPhotoIdList()));
        }
        if (!CollectionUtils.isEmpty(realPropertyRequestDto.getVirtualTourImageIdList())) {
            realProperty.getFilesMap().put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(realPropertyRequestDto.getVirtualTourImageIdList()));
        }
        Application application = new Application();
        if (nonNull(dto.getId())) {
            Optional<Application> optionalApplication = applicationRepository.findById(dto.getId());
            if (optionalApplication.isPresent()) {
                application.setId(optionalApplication.get().getId());
                realProperty.setId(optionalApplication.get().getRealProperty().getId());
            }
        }
        application.setRealProperty(realProperty);
        application.setOwner(owner);
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
        application.setAmount(dto.getAmount());
        application.setCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice());
        application.setApplicationStatus(applicationStatusRepository.findByCode(ApplicationStatus.NEW));
        return applicationRepository.save(application).getId();
    }

    private <T> T mapDict(Class<T> clazz, Long id) {
        if (nonNull(id) && id != 0L) {
            return entityManager.getReference(clazz, id);
        }
        return null;
    }

    private void setInitStatus(Application.ApplicationBuilder builder) {
        ApplicationStatus status = applicationStatusRepository.findByCode("002001");
        builder.applicationStatus(status);
    }

    private RealPropertyOwner getOwner(RealPropertyOwnerDto dto) {
        RealPropertyOwner owner;
        if (dto.getId() == null || dto.getId() == 0L) {
            owner = RealPropertyOwner.builder()
                    .firstName(dto.getFirstName())
                    .surname(dto.getSurname())
                    .patronymic(dto.getPatronymic())
                    .phoneNumber(dto.getPhoneNumber())
                    .email(dto.getEmail())
                    .gender(dto.getGender())
                    .build();
            ownerRepository.save(owner);
        } else {
            owner = ownerRepository.getOne(dto.getId());
        }
        return owner;
    }

    @Override
    public void update(String token, Long aLong, ApplicationDto input) {
        input.setId(aLong);
        save(token, input);
    }

    @Override
    public void deleteById(String token, Long aLong) {
        applicationRepository.deleteById(aLong);
    }
}
