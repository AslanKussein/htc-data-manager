package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyFile;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.dictionary.*;
import kz.dilau.htcdatamanager.service.ApplicationViewClientService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
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

    private final ApplicationRepository applicationRepository;
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
    public ApplicationViewClientDTO getByIdForClient(Long id) {
        Application application = applicationRepository.getOne(id);
        return mapToApplicationDto(application);
    }

    private Boolean isSell(Application application) {
        return application.getOperationTypeId() == 1;
    }

    private Boolean isFlat(Application application) {
        return application.getObjectTypeId() == 1;
    }

    private ApplicationViewClientDTO mapToApplicationDto(Application application) {
        ApplicationViewClientDTO.ApplicationViewClientDTOBuilder dto = ApplicationViewClientDTO.builder()
                .id(application.getId())
                .createdDate(application.getCreatedDate())
                .operationType(nonNull(application.getOperationTypeId()) ? operationTypeRepository.getOne(application.getOperationTypeId()).getMultiLang() : null)
                .objectType(nonNull(application.getObjectTypeId()) ? objectTypeRepository.getOne(application.getObjectTypeId()).getMultiLang() : null)
                .isSell(isSell(application))
                .isFlat(isFlat(application));
        if (isSell(application)) {
            ApplicationPurchaseDataDto dataDto = new ApplicationPurchaseDataDto(application.getApplicationPurchaseData());
            dto.comment(dataDto.getNote())
                    .mortgage(dataDto.getMortgage())
                    .objectPricePeriod(dataDto.getObjectPricePeriod())
                    .district(nonNull(dataDto.getDistrictId()) ? districtRepository.getOne(dataDto.getDistrictId()).getMultiLang() : null)
                    .probabilityOfBidding(dataDto.getProbabilityOfBidding());
        }
        if (!isSell(application)) {
            ApplicationSellDataDto sellData = new ApplicationSellDataDto(application.getApplicationSellData());
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

        return dto.build();
    }

    private void fillRealProperty(ApplicationViewClientDTO.ApplicationViewClientDTOBuilder dto, Application application) {
        RealProperty realProperty = application.getApplicationSellData().getRealProperty();
        if (isNull(realProperty)) {
            return;
        }

        BuildingDto buildingDto = new BuildingDto(realProperty.getBuilding());
        dto.district(nonNull(buildingDto.getDistrictId()) ? districtRepository.getOne(buildingDto.getDistrictId()).getMultiLang() : null)
                .fullAddress(DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber()));
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
        RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
        if (nonNull(metadata)) {
            dto.numberOfRooms(metadata.getNumberOfRooms())
                    .floor(metadata.getFloor())
                    .apartmentNumber(realProperty.getApartmentNumber())
                    .totalArea(metadata.getTotalArea())
                    .livingArea(metadata.getLivingArea())
                    .kitchenArea(metadata.getKitchenArea())
                    .balconyArea(metadata.getBalconyArea())
                    .numberOfBedrooms(metadata.getNumberOfBedrooms())
                    .atelier(metadata.getAtelier())
                    .separateBathroom(metadata.getSeparateBathroom());
        }
        RealPropertyFile realPropertyFile = realProperty.getFileByStatus(MetadataStatus.APPROVED);
        if (nonNull(realPropertyFile)) {
            dto.photoIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.PHOTO))
                    .housingPlanImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN))
                    .virtualTourImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR));
        }

        GeneralCharacteristicsDto generalCharacteristicsDto = new GeneralCharacteristicsDto(metadata.getGeneralCharacteristics());
        dto.ceilingHeight(generalCharacteristicsDto.getCeilingHeight())
                .numberOfFloors(generalCharacteristicsDto.getNumberOfFloors())
                .apartmentsOnTheSite(generalCharacteristicsDto.getApartmentsOnTheSite())
                .yearOfConstruction(generalCharacteristicsDto.getYearOfConstruction())
                .concierge(generalCharacteristicsDto.getConcierge())
                .playground(generalCharacteristicsDto.getPlayground())
                .wheelchair(generalCharacteristicsDto.getWheelchair());
        if (nonNull(generalCharacteristicsDto.getMaterialOfConstructionId())) {
            dto.materialOfConstruction(materialOfConstructionRepository.getOne(generalCharacteristicsDto.getMaterialOfConstructionId()).getMultiLang());
        }
        if (nonNull(generalCharacteristicsDto.getTypeOfElevatorList())) {
            dto.typeOfElevatorList(generalCharacteristicsDto.getTypeOfElevatorList().stream()
                    .filter(aLong -> aLong != 0).map(aLong -> typeOfElevatorRepository.getOne(aLong).getMultiLang())
                    .collect(Collectors.toList()));
        }
        if (nonNull(generalCharacteristicsDto.getYardTypeId())) {
            dto.yardType(yardTypeRepository.getOne(generalCharacteristicsDto.getYardTypeId()).getMultiLang());
        }
        if (nonNull(generalCharacteristicsDto.getParkingTypeIds())) {
            dto.parkingTypes(generalCharacteristicsDto.getParkingTypeIds().stream().filter(aLong -> aLong != 0)
                    .map(aLong -> parkingTypeRepository.getOne(aLong).getMultiLang()).collect(Collectors.toList()));
        }
        if (nonNull(generalCharacteristicsDto.getHouseConditionId())) {
            dto.houseCondition(houseConditionRepository.getOne(generalCharacteristicsDto.getHouseConditionId()).getMultiLang());
        }
    }
}
