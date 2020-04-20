package kz.dilau.htcdatamanager.component.application;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
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

@RequiredArgsConstructor
@Service
public class ApplicationManagerImpl implements ApplicationManager {
    private final ApplicationRepository aRepository;
    private final RealPropertyOwnerRepository rpoRepository;
    private final EntityManager entityManager;
    private final ApplicationStatusRepository asRepository;
    private final PurchaseInfoRepository piRepository;
    private final GeneralCharacteristicsRepository gcRepository;
    private final RealPropertyRepository rpRepository;

    @Override
    public ApplicationDto getById(String token, Long aLong) {
        return null;
    }

    @Override
    public List<ApplicationDto> getAll(String token) {
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public Long save(String token, ApplicationDto dto) {
        Application.ApplicationBuilder builder = Application.builder();
        RealPropertyOwner owner = getOwner(dto);
        Long operationTypeId = dto.getOperationTypeId();
        OperationType operationType = entityManager.getReference(OperationType.class, operationTypeId);
        if (dto.getObjectTypeId() != null && dto.getObjectTypeId() != 0L) {
            ObjectType objectType = entityManager.getReference(ObjectType.class, dto.getObjectTypeId());
            RealProperty.RealPropertyBuilder rpBuilder = RealProperty.builder()
                    .objectType(objectType)
                    .cadastralNumber(dto.getCadastralNumber())
                    .floor(dto.getFloor())
                    .apartmentNumber(dto.getApartmentNumber())
                    .numberOfRooms(dto.getNumberOfRooms())
                    .totalArea(dto.getTotalArea())
                    .livingArea(dto.getLivingArea())
                    .kitchenArea(dto.getKitchenArea())
                    .balconyArea(dto.getBalconyArea())
                    .numberOfBedrooms(dto.getNumberOfBedrooms())
                    .atelier(dto.getAtelier())
                    .separateBathroom(dto.getSeparateBathroom())
                    .landArea(dto.getLandArea());
            if (dto.getSewerageId() != null && dto.getSewerageId() != 0L) {
                Sewerage sewerage = entityManager.getReference(Sewerage.class, dto.getSewerageId());
                rpBuilder.sewerage(sewerage);
            }
            if (dto.getHeatingSystemId() != null && dto.getHeatingSystemId() != 0L) {
                HeatingSystem heatingSystem = entityManager.getReference(HeatingSystem.class, dto.getHeatingSystemId());
                rpBuilder.heatingSystem(heatingSystem);
            }
            boolean b = false;
            if (dto.getResidentialComplexId() != null && dto.getResidentialComplexId() != 0L) {
                ResidentialComplex residentialComplex = entityManager.getReference(ResidentialComplex.class, dto.getResidentialComplexId());
                rpBuilder.residentialComplex(residentialComplex);
                b = true;
            }
            RealProperty realProperty = rpBuilder.build();
            Long id = rpRepository.save(realProperty).getId();
            if (!b) {
                GeneralCharacteristics.GeneralCharacteristicsBuilder gcBuilder = GeneralCharacteristics.builder()
                        .houseNumber(dto.getHouseNumber())
                        .houseNumberFraction(dto.getHouseNumberFraction())
                        .ceilingHeight(dto.getCeilingHeight())
                        .housingClass(dto.getHousingClass())
                        .housingCondition(dto.getHousingCondition())
                        .yearOfConstruction(dto.getYearOfConstruction())
                        .numberOfFloors(dto.getNumberOfFloors())
                        .numberOfApartments(dto.getNumberOfApartments())
                        .apartmentsOnTheSite(dto.getApartmentsOnTheSite())
                        .concierge(dto.getConcierge())
                        .wheelchair(dto.getWheelchair())
                        .playground(dto.getPlayground());
                if (dto.getCityId() != null && dto.getCityId() != 0L) {
                    City city = entityManager.getReference(City.class, dto.getCityId());
                    gcBuilder.city(city);
                }
                if (dto.getDistrictId() != null && dto.getDistrictId() != 0L) {
                    District district = entityManager.getReference(District.class, dto.getDistrictId());
                    gcBuilder.district(district);
                }
                if (dto.getStreetId() != null && dto.getStreetId() != 0L) {
                    Street street = entityManager.getReference(Street.class, dto.getStreetId());
                    gcBuilder.street(street);
                }
                if (dto.getPropertyDeveloperId() != null && dto.getPropertyDeveloperId() != 0L) {
                    PropertyDeveloper propertyDeveloper = entityManager.getReference(PropertyDeveloper.class, dto.getPropertyDeveloperId());
                    gcBuilder.propertyDeveloper(propertyDeveloper);
                }
                if (dto.getMaterialOfConstructionId() != null && dto.getMaterialOfConstructionId() != 0L) {
                    MaterialOfConstruction materialOfConstruction = entityManager.getReference(MaterialOfConstruction.class, dto.getMaterialOfConstructionId());
                    gcBuilder.materialOfConstruction(materialOfConstruction);
                }
                if (dto.getParkingTypeId() != null && dto.getParkingTypeId() != 0L) {
                    ParkingType parkingType = entityManager.getReference(ParkingType.class, dto.getParkingTypeId());
                    gcBuilder.parkingType(parkingType);
                }
                if (dto.getYardTypeId() != null && dto.getYardTypeId() != 0L) {
                    YardType yardType = entityManager.getReference(YardType.class, dto.getYardTypeId());
                    gcBuilder.yardType(yardType);
                }
                gcBuilder.id(id);
                GeneralCharacteristics generalCharacteristics = gcBuilder.build();
                gcRepository.save(generalCharacteristics);
                realProperty.setGeneralCharacteristics(generalCharacteristics);
                rpRepository.save(realProperty);
            }
            //typesOfElevator

            if (operationTypeId == 1L) {
                PurchaseInfo purchaseInfo = PurchaseInfo.builder()
                        .objectPriceFrom(dto.getObjectPriceFrom())
                        .objectPriceTo(dto.getObjectPriceTo())
                        .floorFrom(dto.getFloorFrom())
                        .floorTo(dto.getFloorTo())
                        .numberOfRoomsFrom(dto.getNumberOfRoomsFrom())
                        .numberOfRoomsFrom(dto.getNumberOfRoomsFrom())
                        .totalAreaFrom(dto.getTotalAreaFrom())
                        .totalAreaTo(dto.getTotalAreaTo())
                        .livingAreaFrom(dto.getLivingAreaFrom())
                        .livingAreaTo(dto.getLivingAreaTo())
                        .kitchenAreaFrom(dto.getKitchenAreaFrom())
                        .kitchenAreaTo(dto.getKitchenAreaTo())
                        .balconyAreaFrom(dto.getBalconyAreaFrom())
                        .balconyAreaTo(dto.getBalconyAreaTo())
                        .ceilingHeightFrom(dto.getCeilingHeightFrom())
                        .ceilingHeightTo(dto.getCeilingHeightTo())
                        .numberOfBedroomsFrom(dto.getNumberOfBedroomsFrom())
                        .numberOfBedroomsTo(dto.getNumberOfBedroomsTo())
                        .landAreaFrom(dto.getLandAreaFrom())
                        .landAreaTo(dto.getLandAreaTo())
                        .numberOfFloorsFrom(dto.getNumberOfFloorsFrom())
                        .numberOfFloorsTo(dto.getNumberOfFloorsTo())
                        .id(id)
                        .build();
                piRepository.save(purchaseInfo);
                realProperty.setPurchaseInfo(purchaseInfo);
                rpRepository.save(realProperty);
                if (!CollectionUtils.isEmpty(dto.getHousingPlanImageIdList())) {
                    realProperty.getFilesMap().put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(dto.getHousingPlanImageIdList()));
                }
                if (!CollectionUtils.isEmpty(dto.getPhotoIdList())) {
                    realProperty.getFilesMap().put(RealPropertyFileType.PHOTO, new HashSet<>(dto.getPhotoIdList()));
                }
                if (!CollectionUtils.isEmpty(dto.getVirtualTourImageIdList())) {
                    realProperty.getFilesMap().put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(dto.getVirtualTourImageIdList()));
                }
                rpRepository.save(realProperty);
            }
            builder.realProperty(realProperty);
        }

        builder
                .owner(owner)
                .operationType(operationType)
                .note(dto.getNote())
                .objectPrice(dto.getObjectPrice())
                .mortgage(dto.getMortgage())
                .encumbrance(dto.getEncumbrance())
                .sharedOwnershipProperty(dto.getSharedOwnershipProperty())
                .exchange(dto.getExchange())
                .probabilityOfBidding(dto.getProbabilityOfBidding())
                .theSizeOfTrades(dto.getTheSizeOfTrades())
                .contractPeriod(dto.getContractPeriod())
                .amount(dto.getAmount())
                .isCommissionIncludedInThePrice(dto.isCommissionIncludedInThePrice());
        //possibleReasonsForBidding

        setInitStatus(builder);

        Long id = aRepository.save(builder.build()).getId();
        return id;
    }

    private void setInitStatus(Application.ApplicationBuilder builder) {
        ApplicationStatus status = asRepository.findByCode("002001");
        builder.applicationStatus(status);
    }

    private RealPropertyOwner getOwner(ApplicationDto dto) {
        RealPropertyOwner owner;
        if (dto.getClientId() == null || dto.getClientId() == 0L) {
            owner = RealPropertyOwner.builder()
                    .firstName(dto.getFirstName())
                    .surname(dto.getSurname())
                    .patronymic(dto.getPatronymic())
                    .phoneNumber(dto.getPhoneNumber())
                    .email(dto.getEmail())
                    .gender(dto.getGender())
                    .build();
            rpoRepository.save(owner);
        } else {
            owner = rpoRepository.getOne(dto.getClientId());
        }
        return owner;
    }

    @Override
    public void update(String token, Long aLong, ApplicationDto input) {

    }

    @Override
    public void deleteById(String token, Long aLong) {

    }
}
