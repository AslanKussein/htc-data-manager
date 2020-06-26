package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.dictionary.*;
import kz.dilau.htcdatamanager.service.ApplicationViewService;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationViewServiceImpl implements ApplicationViewService {

    private final ApplicationRepository applicationRepository;


    private final ApplicationFlagRepository applicationFlagRepository;
    private final PossibleReasonForBiddingRepository possibleReasonForBiddingRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final ObjectTypeRepository objectTypeRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;
    private final ResidentialComplexRepository residentialComplexRepository;
    private final MaterialOfConstructionRepository materialOfConstructionRepository;
    private final TypeOfElevatorRepository typeOfElevatorRepository;
    private final ParkingTypeRepository parkingTypeRepository;
    private final YardTypeRepository yardTypeRepository;

    @Override
    public ApplicationViewDTO getById(String token, Long id) {
        Optional<Application> application = applicationRepository.findByIdAndIsRemovedFalse(id);
        return application.map(this::mapToApplicationDto).orElse(null);
    }

    private Boolean isSell(Application application) {
        return application.getOperationType().getCode().equals("001001");
    }

    private ApplicationViewDTO mapToApplicationDto(Application application) {
        ApplicationViewDTO.ApplicationViewDTOBuilder dto = ApplicationViewDTO.builder()
                .id(application.getId())
                .clientLogin(application.getClientLogin())
                .agent(application.getCurrentAgent())
                .operationType(nonNull(application.getOperationTypeId()) ? operationTypeRepository.getOne(application.getOperationTypeId()).getMultiLang() : null)
                .objectType(nonNull(application.getObjectTypeId()) ? objectTypeRepository.getOne(application.getObjectTypeId()).getMultiLang() : null)
                .isSell(isSell(application));
        if (isSell(application)) {
            ApplicationPurchaseDataDto dataDto = new ApplicationPurchaseDataDto(application.getApplicationPurchaseData());
            dto.comment(dataDto.getNote())
                    .mortgage(dataDto.getMortgage())
                    .objectPricePeriod(dataDto.getObjectPricePeriod())
                    .city(nonNull(dataDto.getCityId()) ? cityRepository.getOne(dataDto.getCityId()).getMultiLang() : null)
                    .district(nonNull(dataDto.getDistrictId()) ? districtRepository.getOne(dataDto.getDistrictId()).getMultiLang() : null)
            .probabilityOfBidding(dataDto.getProbabilityOfBidding())
            .theSizeOfTrades(dataDto.getTheSizeOfTrades())
                    .possibleReasonForBiddingIdList(dataDto.getPossibleReasonForBiddingIdList().stream().map(idItem -> possibleReasonForBiddingRepository.getOne(idItem).getMultiLang()).collect(Collectors.toList()))
                    .applicationFlagIdList(dataDto.getApplicationFlagIdList().stream().map(aLong -> applicationFlagRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()))
            ;
        }
        if (!isSell(application)) {
            ApplicationSellDataDto sellData = new ApplicationSellDataDto(application.getApplicationSellData());
            dto.comment(sellData.getNote())
                    .description(sellData.getDescription())
                    .mortgage(sellData.getMortgage())
                    .probabilityOfBidding(sellData.getProbabilityOfBidding())
                    .objectPrice(sellData.getObjectPrice())
                    .possibleReasonForBiddingIdList(sellData.getPossibleReasonForBiddingIdList().stream().map(idItem -> possibleReasonForBiddingRepository.getOne(idItem).getMultiLang()).collect(Collectors.toList()))
                    .theSizeOfTrades(sellData.getTheSizeOfTrades())
                    .encumbrance(sellData.getEncumbrance())
                    .sharedOwnershipProperty(sellData.getSharedOwnershipProperty())
                    .exchange(sellData.getExchange())
            ;
            fillRealProperty(dto, application);
        }
        if (isSell(application)) {
            PurchaseInfoDto purchaseInfoDto = new PurchaseInfoDto(application.getApplicationPurchaseData().getPurchaseInfo());

            dto.numberOfRoomsPeriod(purchaseInfoDto.getNumberOfRoomsPeriod())
                    .floorPeriod(purchaseInfoDto.getFloorPeriod())
                    .totalAreaPeriod(purchaseInfoDto.getTotalAreaPeriod())
                    .livingAreaPeriod(purchaseInfoDto.getLivingAreaPeriod())
                    .kitchenAreaPeriod(purchaseInfoDto.getKitchenAreaPeriod())
                    .balconyAreaPeriod(purchaseInfoDto.getBalconyAreaPeriod())
                    .ceilingHeightPeriod(purchaseInfoDto.getCeilingHeightPeriod())
                    .numberOfBedroomsPeriod(purchaseInfoDto.getNumberOfBedroomsPeriod())
                    .yearOfConstructionPeriod(purchaseInfoDto.getYearOfConstructionPeriod())
                    .concierge(purchaseInfoDto.getConcierge())
                    .wheelchair(purchaseInfoDto.getWheelchair())
                    .yardType(nonNull(purchaseInfoDto.getYardTypeId()) ? yardTypeRepository.getOne(purchaseInfoDto.getYardTypeId()).getMultiLang() : null)
                    .playground(purchaseInfoDto.getPlayground())
                    .typeOfElevatorList(purchaseInfoDto.getTypeOfElevatorList().stream().map(aLong -> typeOfElevatorRepository.getOne(aLong).getMultiLang())
                            .collect(Collectors.toList()))
                    .numberOfFloorsPeriod(purchaseInfoDto.getNumberOfFloorsPeriod())
                    .yearOfConstructionPeriod(purchaseInfoDto.getYearOfConstructionPeriod())
                    .apartmentsOnTheSitePeriod(purchaseInfoDto.getApartmentsOnTheSitePeriod())
                    .parkingTypes(purchaseInfoDto.getParkingTypeIds().stream().map(aLong ->
                            parkingTypeRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()))
                    .atelier(purchaseInfoDto.getAtelier())
                    .separateBathroom(purchaseInfoDto.getSeparateBathroom());
            ;
            if (nonNull(purchaseInfoDto.getMaterialOfConstructionId())) {
                dto.materialOfConstruction(materialOfConstructionRepository.getOne(purchaseInfoDto.getMaterialOfConstructionId()).getMultiLang());
            }
        }

        return dto.build();
    }

    private void fillRealProperty(ApplicationViewDTO.ApplicationViewDTOBuilder dto, Application application) {
        RealPropertyDto realProperty = new RealPropertyDto(application.getApplicationSellData().getRealProperty());

        BuildingDto buildingDto = realProperty.getBuildingDto();
        if (nonNull(buildingDto)) {
            dto.city(nonNull(buildingDto.getCityId()) ? cityRepository.getOne(buildingDto.getCityId()).getMultiLang() : null)
                    .district(nonNull(buildingDto.getDistrictId()) ? districtRepository.getOne(buildingDto.getDistrictId()).getMultiLang() : null)
                    .street(nonNull(buildingDto.getStreetId()) ? streetRepository.getOne(buildingDto.getStreetId()).getMultiLang() : null)
                    .fullAddress(realProperty.getAddress())
                    .latitude(buildingDto.getLatitude())
                    .longitude(buildingDto.getLongitude());
            if (nonNull(buildingDto.getResidentialComplexId())) {
                Optional<ResidentialComplex> residentialComplex = residentialComplexRepository.findById(buildingDto.getResidentialComplexId());
                residentialComplex.ifPresent(complex -> dto.residenceComplex(complex.getHouseName()));
            }
        }
        dto.numberOfRooms(realProperty.getNumberOfRooms())
                .floor(realProperty.getFloor())
                .apartmentNumber(realProperty.getApartmentNumber())
                .totalArea(realProperty.getTotalArea())
                .livingArea(realProperty.getLivingArea())
                .kitchenArea(realProperty.getKitchenArea())
                .balconyArea(realProperty.getBalconyArea())
                .numberOfBedrooms(realProperty.getNumberOfBedrooms())
                .atelier(realProperty.getAtelier())
                .separateBathroom(realProperty.getSeparateBathroom())
                .photoIdList(realProperty.getPhotoIdList())
                .housingPlanImageIdList(realProperty.getHousingPlanImageIdList())
                .virtualTourImageIdList(realProperty.getVirtualTourImageIdList());

        if (nonNull(realProperty.getGeneralCharacteristicsDto())) {
            dto.ceilingHeight(realProperty.getGeneralCharacteristicsDto().getCeilingHeight())
                    .numberOfFloors(realProperty.getGeneralCharacteristicsDto().getNumberOfFloors())
                    .apartmentsOnTheSite(realProperty.getGeneralCharacteristicsDto().getApartmentsOnTheSite())
                    .yearOfConstruction(realProperty.getGeneralCharacteristicsDto().getYearOfConstruction())
                    .typeOfElevatorList(realProperty.getGeneralCharacteristicsDto().getTypeOfElevatorList().stream().map(aLong -> typeOfElevatorRepository.getOne(aLong).getMultiLang())
                            .collect(Collectors.toList()))
                    .concierge(realProperty.getGeneralCharacteristicsDto().getConcierge())
                    .playground(realProperty.getGeneralCharacteristicsDto().getPlayground())
                    .wheelchair(realProperty.getGeneralCharacteristicsDto().getWheelchair())
                    .yardType(nonNull(realProperty.getGeneralCharacteristicsDto().getYardTypeId()) ? yardTypeRepository.getOne(realProperty.getGeneralCharacteristicsDto().getYardTypeId()).getMultiLang() : null)
                    .parkingTypes(realProperty.getGeneralCharacteristicsDto().getParkingTypeIds().stream().map(aLong ->
                            parkingTypeRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()));
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId())) {
                dto.materialOfConstruction(materialOfConstructionRepository.getOne(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId()).getMultiLang());
            }
        }

    }
}
