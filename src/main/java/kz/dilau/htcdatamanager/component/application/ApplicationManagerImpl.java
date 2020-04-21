package kz.dilau.htcdatamanager.component.application;

import kz.dilau.htcdatamanager.component.dataaccess.CheckOperationGroupDto;
import kz.dilau.htcdatamanager.component.dataaccess.DataAccessManager;
import kz.dilau.htcdatamanager.component.dataaccess.ListResponse;
import kz.dilau.htcdatamanager.component.owner.RealPropertyOwnerDto;
import kz.dilau.htcdatamanager.component.property.RealPropertyRequestDto;
import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
//                                dto.setObjectTypeId(application.getObjectType().getId());
//                                dto.setObjectPrice(application.getObjectPrice());
                                dto.setMortgage(application.getMortgage());
                                dto.setEncumbrance(application.getEncumbrance());
                                dto.setSharedOwnershipProperty(application.getSharedOwnershipProperty());
                                dto.setExchange(application.getExchange());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
//                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
                                dto.setTheSizeOfTrades(application.getTheSizeOfTrades());
                                break;
                            case "NOT_ACCESS_ VIEW_SALE_DEAL_INFO":
                                break;
                            case "VIEW_PURCHASE_DEAL_INFO":
                                dto.setOperationTypeId(application.getOperationType().getId());
//                                dto.setObjectTypeId(application.getObjectType().getId());
//                                dto.setObjectPriceFrom(application.getObjectPriceFrom());
//                                dto.setObjectPriceTo(application.getObjectPriceTo());
                                dto.setMortgage(application.getMortgage());
                                dto.setProbabilityOfBidding(application.getProbabilityOfBidding());
//                                dto.setPossibleReasonForBiddingId(application.getPossibleReasonForBidding().getId());//todo
                                break;
                            case "NOT_ACCESS_ VIEW_PURCHASE_DEAL_INFO":
                                break;
                            case "VIEW_DEAL_DATA":
                                dto.setContractPeriod(application.getContractPeriod());
//                                dto.setAmount(application.getAmount());//todo
                                dto.setCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice());
                                break;
                        }
                    }
                });
        return dto;
    }

    private ApplicationDto mapToApplicationDto(Application application) {
        return ApplicationDto.builder().build();
    }

    private RealPropertyOwnerDto mapToOwnerDto(RealPropertyOwner owner) {
        return new RealPropertyOwnerDto(owner);
    }

    private RealPropertyRequestDto mapToRealPropertyDto(RealProperty realProperty) {
        return RealPropertyRequestDto.builder()

                .build();
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
                .objectType(mapDict(ObjectType.class, realPropertyRequestDto.getObjectTypeId()))
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
                .sewerage(mapDict(Sewerage.class, realPropertyRequestDto.getSewerageId()))
                .heatingSystem(mapDict(HeatingSystem.class, realPropertyRequestDto.getHeatingSystemId()))
                .residentialComplex(mapDict(ResidentialComplex.class, realPropertyRequestDto.getResidentialComplexId()))
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
                    .city(mapDict(City.class, realPropertyRequestDto.getCityId()))
                    .district(mapDict(District.class, realPropertyRequestDto.getDistrictId()))
                    .street(mapDict(Street.class, realPropertyRequestDto.getStreetId()))
                    .propertyDeveloper(mapDict(PropertyDeveloper.class, realPropertyRequestDto.getPropertyDeveloperId()))
                    .materialOfConstruction(mapDict(MaterialOfConstruction.class, realPropertyRequestDto.getMaterialOfConstructionId()))
                    .parkingType(mapDict(ParkingType.class, realPropertyRequestDto.getParkingTypeId()))
                    .yardType(mapDict(YardType.class, realPropertyRequestDto.getYardTypeId()))
                    .build();
            realProperty.setGeneralCharacteristics(generalCharacteristics);
        }
        if (operationType.getCode().equals(OperationType.BUY)) {
            PurchaseInfo purchaseInfo = PurchaseInfo.builder()
                    .objectPriceFrom(dto.getObjectPriceFrom())
                    .objectPriceTo(dto.getObjectPriceTo())
                    .floorFrom(realPropertyRequestDto.getFloorFrom())
                    .floorTo(realPropertyRequestDto.getFloorTo())
                    .numberOfRoomsFrom(realPropertyRequestDto.getNumberOfRoomsFrom())
                    .numberOfRoomsFrom(realPropertyRequestDto.getNumberOfRoomsFrom())
                    .totalAreaFrom(realPropertyRequestDto.getTotalAreaFrom())
                    .totalAreaTo(realPropertyRequestDto.getTotalAreaTo())
                    .livingAreaFrom(realPropertyRequestDto.getLivingAreaFrom())
                    .livingAreaTo(realPropertyRequestDto.getLivingAreaTo())
                    .kitchenAreaFrom(realPropertyRequestDto.getKitchenAreaFrom())
                    .kitchenAreaTo(realPropertyRequestDto.getKitchenAreaTo())
                    .balconyAreaFrom(realPropertyRequestDto.getBalconyAreaFrom())
                    .balconyAreaTo(realPropertyRequestDto.getBalconyAreaTo())
                    .ceilingHeightFrom(realPropertyRequestDto.getCeilingHeightFrom())
                    .ceilingHeightTo(realPropertyRequestDto.getCeilingHeightTo())
                    .numberOfBedroomsFrom(realPropertyRequestDto.getNumberOfBedroomsFrom())
                    .numberOfBedroomsTo(realPropertyRequestDto.getNumberOfBedroomsTo())
                    .landAreaFrom(realPropertyRequestDto.getLandAreaFrom())
                    .landAreaTo(realPropertyRequestDto.getLandAreaTo())
                    .numberOfFloorsFrom(realPropertyRequestDto.getNumberOfFloorsFrom())
                    .numberOfFloorsTo(realPropertyRequestDto.getNumberOfFloorsTo())
                    .realProperty(realProperty)
                    .build();
            realProperty.setPurchaseInfo(purchaseInfo);
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

    }

    @Override
    public void deleteById(String token, Long aLong) {

    }
}
