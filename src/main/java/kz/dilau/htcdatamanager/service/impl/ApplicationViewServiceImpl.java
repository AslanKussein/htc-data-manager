package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.dictionary.*;
import kz.dilau.htcdatamanager.repository.filter.ApplicationSpecifications;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ApplicationViewService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import kz.dilau.htcdatamanager.web.rest.KazPostResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationViewServiceImpl implements ApplicationViewService {

    private final ApplicationService applicationService;


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
    private final HouseConditionRepository houseConditionRepository;
    private final HeatingSystemRepository heatingSystemRepository;
    private final SewerageRepository sewerageRepository;
    private final KazPostResource kazPostResource;
    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationViewDTO getById(String token, Long id) {
        ApplicationDto application = applicationService.getById(token, id);
        return mapToApplicationDto(application);
    }

    @Override
    public List<ApplicationViewDTO> getApplicationsForCompare(String token, List<Long> ids) {
        List<ApplicationViewDTO> applications = new ArrayList<>();
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        Specification<Application> specification = ApplicationSpecifications.isRemovedEquals(false);
        if (nonNull(ids)) {
            specification = specification.and(ApplicationSpecifications.applicationIdsIn(ids));
        }
        List<Application> applicationList = applicationRepository.findAll(specification);
        for (Application application : applicationList) {
            List<String> operationList = applicationService.getOperationList(token, application);
            applicationDtos.add(applicationService.mapToApplicationDto(application, operationList));
        }
        applicationDtos.forEach(result -> applications.add(mapToApplicationDto(result)));
        return applications;
    }

    private Boolean isSell(ApplicationDto application) {
        return application.getOperationTypeId() == 1;
    }

    private Boolean isFlat(ApplicationDto application) {
        return application.getObjectTypeId() == 1;
    }

    private ApplicationViewDTO mapToApplicationDto(ApplicationDto application) {
        ApplicationViewDTO.ApplicationViewDTOBuilder dto = ApplicationViewDTO.builder()
                .id(application.getId())
                .clientLogin(application.getClientLogin())
                .agent(application.getAgent())
                .operationType(nonNull(application.getOperationTypeId()) ? DictionaryMappingTool.mapDictionaryToText(operationTypeRepository.getOne(application.getOperationTypeId())) : null)
                .objectType(nonNull(application.getObjectTypeId()) ? DictionaryMappingTool.mapDictionaryToText(objectTypeRepository.getOne(application.getObjectTypeId())) : null)
                .isSell(isSell(application))
                .isFlat(isFlat(application))
                .operationList(application.getOperationList());
        if (isSell(application)) {
            ApplicationPurchaseDataDto dataDto = application.getPurchaseDataDto();
            dto.comment(dataDto.getNote())
                    .mortgage(dataDto.getMortgage())
                    .objectPricePeriod(dataDto.getObjectPricePeriod())
                    .city(nonNull(dataDto.getCityId()) ? DictionaryMappingTool.mapDictionaryToText(cityRepository.getOne(dataDto.getCityId())) : null)
                    .probabilityOfBidding(dataDto.getProbabilityOfBidding())
                    .theSizeOfTrades(dataDto.getTheSizeOfTrades());
            if (nonNull(dataDto.getDistricts())) {
                dto.districts(dataDto.getDistricts().stream()
                        .filter(aLong -> aLong != 0)
                        .map(idItem -> DictionaryMappingTool.mapDictionaryToText(districtRepository.getOne(idItem)))
                        .collect(Collectors.toList()));
            }
            if (nonNull(dataDto.getPossibleReasonForBiddingIdList())) {
                dto.possibleReasonForBiddingIdList(dataDto.getPossibleReasonForBiddingIdList().stream()
                        .filter(aLong -> aLong != 0)
                        .map(idItem -> DictionaryMappingTool.mapDictionaryToText(possibleReasonForBiddingRepository.getOne(idItem)))
                        .collect(Collectors.toList()));
            }
            if (nonNull(dataDto.getApplicationFlagIdList())) {
                dto.applicationFlagIdList(dataDto.getApplicationFlagIdList().stream()
                        .filter(aLong -> aLong != 0)
                        .map(aLong -> DictionaryMappingTool.mapDictionaryToText(applicationFlagRepository.getOne(aLong)))
                        .collect(Collectors.toList()));
            }
        }
        if (!isSell(application)) {
            ApplicationSellDataDto sellData = application.getSellDataDto();
            if (nonNull(sellData)) {
                dto.comment(sellData.getNote())
                        .description(sellData.getDescription())
                        .mortgage(sellData.getMortgage())
                        .probabilityOfBidding(sellData.getProbabilityOfBidding())
                        .objectPrice(sellData.getObjectPrice())
                        .theSizeOfTrades(sellData.getTheSizeOfTrades())
                        .encumbrance(sellData.getEncumbrance())
                        .sharedOwnershipProperty(sellData.getSharedOwnershipProperty())
                        .exchange(sellData.getExchange());
                if (nonNull(sellData.getPossibleReasonForBiddingIdList())) {
                    dto.possibleReasonForBiddingIdList(sellData.getPossibleReasonForBiddingIdList().stream()
                            .filter(aLong -> aLong != 0)
                            .map(idItem -> DictionaryMappingTool.mapDictionaryToText(possibleReasonForBiddingRepository.getOne(idItem)))
                            .collect(Collectors.toList()));
                }
                if (nonNull(sellData.getApplicationFlagIdList())) {
                    dto.applicationFlagIdList(sellData.getApplicationFlagIdList().stream()
                            .filter(aLong -> aLong != 0)
                            .map(idItem -> DictionaryMappingTool.mapDictionaryToText(applicationFlagRepository.getOne(idItem)))
                            .collect(Collectors.toList()));
                }
            }
            fillRealProperty(dto, application);
        }
        if (isSell(application)) {
            PurchaseInfoDto purchaseInfoDto = application.getPurchaseInfoDto();
            if (nonNull(purchaseInfoDto)) {
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
                        .playground(purchaseInfoDto.getPlayground())
                        .numberOfFloorsPeriod(purchaseInfoDto.getNumberOfFloorsPeriod())
                        .yearOfConstructionPeriod(purchaseInfoDto.getYearOfConstructionPeriod())
                        .apartmentsOnTheSitePeriod(purchaseInfoDto.getApartmentsOnTheSitePeriod())
                        .atelier(purchaseInfoDto.getAtelier())
                        .separateBathroom(purchaseInfoDto.getSeparateBathroom())
                        .landAreaPeriod(purchaseInfoDto.getLandAreaPeriod());
                if (nonNull(purchaseInfoDto.getMaterialOfConstructionId())) {
                    dto.materialOfConstruction(DictionaryMappingTool.mapDictionaryToText(materialOfConstructionRepository.getOne(purchaseInfoDto.getMaterialOfConstructionId())));
                }
                if (nonNull(purchaseInfoDto.getYardTypeId())) {
                    dto.yardType(nonNull(purchaseInfoDto.getYardTypeId()) ? DictionaryMappingTool.mapDictionaryToText(yardTypeRepository.getOne(purchaseInfoDto.getYardTypeId())) : null);
                }
                if (nonNull(purchaseInfoDto.getParkingTypeIds())) {
                    dto.parkingTypes(purchaseInfoDto.getParkingTypeIds().stream()
                            .filter(aLong -> aLong != 0)
                            .map(aLong -> DictionaryMappingTool.mapDictionaryToText(parkingTypeRepository.getOne(aLong)))
                            .collect(Collectors.toList()));
                }
                if (nonNull(purchaseInfoDto.getTypeOfElevatorList())) {
                    dto.typeOfElevatorList(purchaseInfoDto.getTypeOfElevatorList().stream()
                            .filter(aLong -> aLong != 0)
                            .map(aLong -> DictionaryMappingTool.mapDictionaryToText(typeOfElevatorRepository.getOne(aLong)))
                            .collect(Collectors.toList()));
                }
            }
        }

        return dto.build();
    }

    private void fillRealProperty(ApplicationViewDTO.ApplicationViewDTOBuilder dto, ApplicationDto application) {
        if (isNull(application.getRealPropertyDto())) {
            return;
        }
        RealPropertyDto realProperty = application.getRealPropertyDto();

        BuildingDto buildingDto = realProperty.getBuildingDto();
        if (nonNull(buildingDto)) {
            dto.city(nonNull(buildingDto.getCityId()) ? DictionaryMappingTool.mapDictionaryToText(cityRepository.getOne(buildingDto.getCityId())) : null)
                    .street(nonNull(buildingDto.getStreetId()) ? DictionaryMappingTool.mapDictionaryToText(streetRepository.getOne(buildingDto.getStreetId())) : null)
                    .fullAddress(realProperty.getAddress())
                    .latitude(buildingDto.getLatitude())
                    .longitude(buildingDto.getLongitude())
                    .houseNumber(buildingDto.getHouseNumber());
            if (nonNull(buildingDto.getDistrictId())) {
                List<MultiLangText> districts = new ArrayList<>();
                districts.add(DictionaryMappingTool.mapDictionaryToText(districtRepository.getOne(buildingDto.getDistrictId())));
                dto.districts(districts);
            }
            if (nonNull(buildingDto.getResidentialComplexId())) {
                Optional<ResidentialComplex> residentialComplex = residentialComplexRepository.findById(buildingDto.getResidentialComplexId());
                residentialComplex.ifPresent(complex -> dto.residenceComplex(complex.getHouseName()));
            }
            if (nonNull(buildingDto.getPostcode())) {
                ResponseEntity<KazPostDTO> address = kazPostResource.getPostData(buildingDto.getPostcode());
                if (nonNull(address) && nonNull(address.getBody())) {
                    MultiLangText text = new MultiLangText();
                    text.setNameRu(address.getBody().getAddressRus());
                    text.setNameKz(address.getBody().getAddressKaz());
                    dto.fullAddress(text);
                }
                dto.postcode(buildingDto.getPostcode());
            }
        }
        dto.numberOfRooms(realProperty.getNumberOfRooms())
                .floor(realProperty.getFloor())
                .landArea(realProperty.getLandArea())
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
                .virtualTourImageIdList(realProperty.getVirtualTourImageIdList())
                .heatingSystem(nonNull(realProperty.getHeatingSystemId()) ? DictionaryMappingTool.mapDictionaryToText(heatingSystemRepository.getOne(realProperty.getHeatingSystemId())) : null)
                .sewerage(nonNull(realProperty.getSewerageId()) ? DictionaryMappingTool.mapDictionaryToText(sewerageRepository.getOne(realProperty.getSewerageId())) : null);

        if (nonNull(realProperty.getGeneralCharacteristicsDto())) {
            dto.ceilingHeight(realProperty.getGeneralCharacteristicsDto().getCeilingHeight())
                    .numberOfFloors(realProperty.getGeneralCharacteristicsDto().getNumberOfFloors())
                    .apartmentsOnTheSite(realProperty.getGeneralCharacteristicsDto().getApartmentsOnTheSite())
                    .yearOfConstruction(realProperty.getGeneralCharacteristicsDto().getYearOfConstruction())
                    .concierge(realProperty.getGeneralCharacteristicsDto().getConcierge())
                    .playground(realProperty.getGeneralCharacteristicsDto().getPlayground())
                    .wheelchair(realProperty.getGeneralCharacteristicsDto().getWheelchair());
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId())) {
                dto.materialOfConstruction(DictionaryMappingTool.mapDictionaryToText(materialOfConstructionRepository.getOne(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId())));
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getTypeOfElevatorList())) {
                dto.typeOfElevatorList(realProperty.getGeneralCharacteristicsDto().getTypeOfElevatorList().stream()
                        .filter(aLong -> aLong != 0)
                        .map(aLong -> DictionaryMappingTool.mapDictionaryToText(typeOfElevatorRepository.getOne(aLong)))
                        .collect(Collectors.toList()));
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getYardTypeId())) {
                dto.yardType(nonNull(realProperty.getGeneralCharacteristicsDto().getYardTypeId()) ? DictionaryMappingTool.mapDictionaryToText(yardTypeRepository.getOne(realProperty.getGeneralCharacteristicsDto().getYardTypeId())) : null);
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getParkingTypeIds())) {
                dto.parkingTypes(realProperty.getGeneralCharacteristicsDto().getParkingTypeIds().stream()
                        .filter(aLong -> aLong != 0)
                        .map(aLong -> DictionaryMappingTool.mapDictionaryToText(parkingTypeRepository.getOne(aLong)))
                        .collect(Collectors.toList()));
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getHouseConditionId())) {
                dto.houseCondition(DictionaryMappingTool.mapDictionaryToText(houseConditionRepository.getOne(realProperty.getGeneralCharacteristicsDto().getHouseConditionId())));
            }
        }

    }
}
