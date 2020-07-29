package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationViewClientService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationViewClientDTO;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import kz.dilau.htcdatamanager.web.rest.KazPostResource;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApplicationViewClientServiceImpl implements ApplicationViewClientService {

    private final ApplicationRepository applicationRepository;
    private final EntityService entityService;
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
                .operationType(DictionaryMappingTool.mapDictionaryToText(application.getOperationType()))
                .objectType(DictionaryMappingTool.mapDictionaryToText(application.getObjectType()))
                .isSell(isSell(application))
                .isFlat(isFlat(application));
        if (isSell(application)) {
            ApplicationPurchaseData applicationPurchaseData = application.getApplicationPurchaseData();
            dto.comment(applicationPurchaseData.getNote())
                    .mortgage(applicationPurchaseData.getMortgage())
                    .probabilityOfBidding(applicationPurchaseData.getProbabilityOfBidding());
            if (nonNull(applicationPurchaseData.getPurchaseInfo())) {
                PurchaseInfo purchaseInfo = applicationPurchaseData.getPurchaseInfo();
                dto.objectPricePeriod(new BigDecimalPeriod(purchaseInfo.getObjectPriceFrom(), purchaseInfo.getObjectPriceTo()));
            }
            if (!applicationPurchaseData.getDistricts().isEmpty()) {
                dto.districts(applicationPurchaseData.getDistricts().stream()
                        .map(DictionaryMappingTool::mapDictionaryToText)
                        .collect(Collectors.toList()));
            }
        }
        if (!isSell(application)) {
            ApplicationSellData sellData = application.getApplicationSellData();
            dto.comment(sellData.getNote())
                    .mortgage(sellData.getMortgage())
                    .probabilityOfBidding(sellData.getProbabilityOfBidding())
                    .objectPrice(sellData.getObjectPrice())
                    .encumbrance(sellData.getEncumbrance())
                    .sharedOwnershipProperty(sellData.getSharedOwnershipProperty())
                    .exchange(sellData.getExchange());

            fillRealProperty(dto, application);
        }
        if (isSell(application) && nonNull(application.getApplicationPurchaseData()) && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
            PurchaseInfo purchaseInfo = application.getApplicationPurchaseData().getPurchaseInfo();
            dto.numberOfRoomsPeriod(new IntegerPeriod(purchaseInfo.getNumberOfRoomsFrom(), purchaseInfo.getNumberOfRoomsTo()))
                    .floorPeriod(new IntegerPeriod(purchaseInfo.getFloorFrom(), purchaseInfo.getFloorTo()))
                    .totalAreaPeriod(new BigDecimalPeriod(purchaseInfo.getTotalAreaFrom(), purchaseInfo.getTotalAreaTo()))
                    .livingAreaPeriod(new BigDecimalPeriod(purchaseInfo.getLivingAreaFrom(), purchaseInfo.getLivingAreaTo()))
                    .kitchenAreaPeriod(new BigDecimalPeriod(purchaseInfo.getKitchenAreaFrom(), purchaseInfo.getKitchenAreaTo()))
                    .balconyAreaPeriod(new BigDecimalPeriod(purchaseInfo.getBalconyAreaFrom(), purchaseInfo.getBalconyAreaTo()))
                    .ceilingHeightPeriod(new BigDecimalPeriod(purchaseInfo.getCeilingHeightFrom(), purchaseInfo.getCeilingHeightTo()))
                    .numberOfBedroomsPeriod(new IntegerPeriod(purchaseInfo.getNumberOfBedroomsFrom(), purchaseInfo.getNumberOfBedroomsTo()))
                    .yearOfConstructionPeriod(new IntegerPeriod(purchaseInfo.getYearOfConstructionFrom(), purchaseInfo.getYearOfConstructionTo()))
                    .concierge(purchaseInfo.getConcierge())
                    .wheelchair(purchaseInfo.getWheelchair())
                    .playground(purchaseInfo.getPlayground())
                    .atelier(purchaseInfo.getAtelier())
                    .separateBathroom(purchaseInfo.getSeparateBathroom())
                    .materialOfConstruction(DictionaryMappingTool.mapDictionaryToText(purchaseInfo.getMaterialOfConstruction()))
                    .yardType(DictionaryMappingTool.mapDictionaryToText(purchaseInfo.getYardType()));
            if (!purchaseInfo.getTypesOfElevator().isEmpty()) {
                dto.typeOfElevatorList(purchaseInfo.getTypesOfElevator().stream()
                        .filter(aLong -> nonNull(aLong) && nonNull(aLong.getId()))
                        .map(aLong -> DictionaryMappingTool.mapDictionaryToText(entityService.mapEntity(TypeOfElevator.class, aLong.getId())))
                        .collect(Collectors.toList()));
            }
            if (!purchaseInfo.getParkingTypes().isEmpty()) {
                dto.parkingTypes(purchaseInfo.getParkingTypes().stream()
                        .filter(aLong -> nonNull(aLong) && nonNull(aLong.getId()))
                        .map(aLong -> DictionaryMappingTool.mapDictionaryToText(entityService.mapEntity(ParkingType.class, aLong.getId())))
                        .collect(Collectors.toList()));
            }
        }

        return dto.build();
    }

    private void fillRealProperty(ApplicationViewClientDTO.ApplicationViewClientDTOBuilder dto, Application application) {
        RealProperty realProperty = application.getApplicationSellData().getRealProperty();
        if (isNull(realProperty) || isNull(realProperty.getBuilding())) {
            return;
        }
        Building building = realProperty.getBuilding();
        List<MultiLangText> districts = new ArrayList<>();
        districts.add(DictionaryMappingTool.mapDictionaryToText(building.getDistrict()));
        dto.districts(districts)
                .fullAddress(DictionaryMappingTool.mapAddressToMultiLang(building, realProperty.getApartmentNumber()));
        if (nonNull(building.getResidentialComplex())) {
            dto.residenceComplex(building.getResidentialComplex().getHouseName());
        }
        if (nonNull(building.getPostcode())) {
            ResponseEntity<KazPostDTO> address = kazPostResource.getPostData(building.getPostcode());
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

        GeneralCharacteristics generalCharacteristics = metadata.getGeneralCharacteristics();
        dto.ceilingHeight(generalCharacteristics.getCeilingHeight())
                .numberOfFloors(generalCharacteristics.getNumberOfFloors())
                .apartmentsOnTheSite(generalCharacteristics.getApartmentsOnTheSite())
                .yearOfConstruction(generalCharacteristics.getYearOfConstruction())
                .concierge(generalCharacteristics.getConcierge())
                .playground(generalCharacteristics.getPlayground())
                .wheelchair(generalCharacteristics.getWheelchair())
                .materialOfConstruction(DictionaryMappingTool.mapDictionaryToText(generalCharacteristics.getMaterialOfConstruction()))
                .yardType(DictionaryMappingTool.mapDictionaryToText(generalCharacteristics.getYardType()))
                .houseCondition(DictionaryMappingTool.mapDictionaryToText(generalCharacteristics.getHouseCondition()));
        if (!generalCharacteristics.getTypesOfElevator().isEmpty()) {
            dto.typeOfElevatorList(generalCharacteristics.getTypesOfElevator().stream()
                    .filter(aLong -> nonNull(aLong) && nonNull(aLong.getId()))
                    .map(aLong -> DictionaryMappingTool.mapDictionaryToText(entityService.mapEntity(TypeOfElevator.class, aLong.getId())))
                    .collect(Collectors.toList()));
        }
        if (!generalCharacteristics.getParkingTypes().isEmpty()) {
            dto.parkingTypes(generalCharacteristics.getParkingTypes().stream()
                    .filter(aLong -> nonNull(aLong) && nonNull(aLong.getId()))
                    .map(aLong -> DictionaryMappingTool.mapDictionaryToText(entityService.mapEntity(ParkingType.class, aLong.getId())))
                    .collect(Collectors.toList()));
        }
    }
}
