package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.repository.dictionary.*;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.ApplicationViewClientService;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import kz.dilau.htcdatamanager.web.rest.KazPostResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationViewClientServiceImpl implements ApplicationViewClientService {

    private final ApplicationService applicationService;
    private final OperationTypeRepository operationTypeRepository;
    private final ObjectTypeRepository objectTypeRepository;
    private final DistrictRepository districtRepository;
    private final ResidentialComplexRepository residentialComplexRepository;
    private final MaterialOfConstructionRepository materialOfConstructionRepository;
    private final TypeOfElevatorRepository typeOfElevatorRepository;
    private final ParkingTypeRepository parkingTypeRepository;
    private final YardTypeRepository yardTypeRepository;
    private final HouseConditionRepository houseConditionRepository;
    private final KazPostResource kazPostResource;

    @Override
    public ApplicationViewClientDTO getByIdForClient(String token, Long id) {
        ApplicationDto application = applicationService.getById(token, id);
        return mapToApplicationDto(application);
    }

    private Boolean isSell(ApplicationDto application) {
        return application.getOperationTypeId() == 1;
    }

    private Boolean isFlat(ApplicationDto application) {
        return application.getObjectTypeId() == 1;
    }

    private ApplicationViewClientDTO mapToApplicationDto(ApplicationDto application) {
        ApplicationViewClientDTO.ApplicationViewClientDTOBuilder dto = ApplicationViewClientDTO.builder()
                .id(application.getId())
                .operationType(nonNull(application.getOperationTypeId()) ? operationTypeRepository.getOne(application.getOperationTypeId()).getMultiLang() : null)
                .objectType(nonNull(application.getObjectTypeId()) ? objectTypeRepository.getOne(application.getObjectTypeId()).getMultiLang() : null)
                .isSell(isSell(application))
                .isFlat(isFlat(application));
        if (isSell(application)) {
            ApplicationPurchaseDataDto dataDto = application.getPurchaseDataDto();
            dto.comment(dataDto.getNote())
                    .mortgage(dataDto.getMortgage())
                    .objectPricePeriod(dataDto.getObjectPricePeriod())
                    .district(nonNull(dataDto.getDistrictId()) ? districtRepository.getOne(dataDto.getDistrictId()).getMultiLang() : null)
                    .probabilityOfBidding(dataDto.getProbabilityOfBidding());
        }
        if (!isSell(application)) {
            ApplicationSellDataDto sellData = application.getSellDataDto();
            dto.comment(sellData.getNote())
                    .mortgage(sellData.getMortgage())
                    .probabilityOfBidding(sellData.getProbabilityOfBidding())
                    .objectPrice(sellData.getObjectPrice())
                    .encumbrance(sellData.getEncumbrance())
                    .sharedOwnershipProperty(sellData.getSharedOwnershipProperty())
                    .exchange(sellData.getExchange());
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
                        .atelier(purchaseInfoDto.getAtelier())
                        .separateBathroom(purchaseInfoDto.getSeparateBathroom());
                if (nonNull(purchaseInfoDto.getMaterialOfConstructionId())) {
                    dto.materialOfConstruction(materialOfConstructionRepository.getOne(purchaseInfoDto.getMaterialOfConstructionId()).getMultiLang());
                }
                if (nonNull(purchaseInfoDto.getYardTypeId())) {
                    dto.yardType(nonNull(purchaseInfoDto.getYardTypeId()) ? yardTypeRepository.getOne(purchaseInfoDto.getYardTypeId()).getMultiLang() : null);
                }
                if (nonNull(purchaseInfoDto.getParkingTypeIds())) {
                    dto.parkingTypes(purchaseInfoDto.getParkingTypeIds().stream()
                            .filter(aLong -> aLong != 0)
                            .map(aLong -> parkingTypeRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()));
                }
                if (nonNull(purchaseInfoDto.getTypeOfElevatorList())) {
                    dto.typeOfElevatorList(purchaseInfoDto.getTypeOfElevatorList().stream()
                            .filter(aLong -> aLong != 0).map(aLong -> typeOfElevatorRepository.getOne(aLong).getMultiLang())
                            .collect(Collectors.toList()));
                }
            }
        }

        return dto.build();
    }

    private void fillRealProperty(ApplicationViewClientDTO.ApplicationViewClientDTOBuilder dto, ApplicationDto application) {
        if (isNull(application.getRealPropertyDto())) {
            return;
        }
        RealPropertyDto realProperty = application.getRealPropertyDto();

        BuildingDto buildingDto = realProperty.getBuildingDto();
        if (nonNull(buildingDto)) {
            dto.district(nonNull(buildingDto.getDistrictId()) ? districtRepository.getOne(buildingDto.getDistrictId()).getMultiLang() : null)
                    .fullAddress(realProperty.getAddress());
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
                    .concierge(realProperty.getGeneralCharacteristicsDto().getConcierge())
                    .playground(realProperty.getGeneralCharacteristicsDto().getPlayground())
                    .wheelchair(realProperty.getGeneralCharacteristicsDto().getWheelchair());
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId())) {
                dto.materialOfConstruction(materialOfConstructionRepository.getOne(realProperty.getGeneralCharacteristicsDto().getMaterialOfConstructionId()).getMultiLang());
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getTypeOfElevatorList())) {
                dto.typeOfElevatorList(realProperty.getGeneralCharacteristicsDto().getTypeOfElevatorList().stream()
                        .filter(aLong -> aLong != 0).map(aLong -> typeOfElevatorRepository.getOne(aLong).getMultiLang())
                        .collect(Collectors.toList()));
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getYardTypeId())) {
                dto.yardType(nonNull(realProperty.getGeneralCharacteristicsDto().getYardTypeId()) ? yardTypeRepository.getOne(realProperty.getGeneralCharacteristicsDto().getYardTypeId()).getMultiLang() : null);
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getParkingTypeIds())) {
                dto.parkingTypes(realProperty.getGeneralCharacteristicsDto().getParkingTypeIds().stream().filter(aLong -> aLong != 0)
                        .map(aLong -> parkingTypeRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()));
            }
            if (nonNull(realProperty.getGeneralCharacteristicsDto().getHouseConditionId())) {
                dto.houseCondition(houseConditionRepository.getOne(realProperty.getGeneralCharacteristicsDto().getHouseConditionId()).getMultiLang());
            }
        }
    }
}