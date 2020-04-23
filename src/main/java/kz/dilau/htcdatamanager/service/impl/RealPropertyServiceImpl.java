package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.repository.RealPropertyRepository;
import kz.dilau.htcdatamanager.service.RealPropertyService;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.util.PeriodUtils.mapToBigDecimalPeriod;
import static kz.dilau.htcdatamanager.util.PeriodUtils.mapToIntegerPeriod;

@Service
@RequiredArgsConstructor
public class RealPropertyServiceImpl implements RealPropertyService {
    private final RealPropertyRepository realPropertyRepository;

    public RealPropertyRequestDto getById(Long id) {
        return mapToRealPropertyDto(realPropertyRepository.getOne(id));
    }

    public List<RealPropertyRequestDto> getAll() {
        List<RealProperty> realPropertyList = realPropertyRepository.findAll();
        List<RealPropertyRequestDto> realPropertyRequestDtoList = new ArrayList<>();
        realPropertyList.forEach(item -> realPropertyRequestDtoList.add(mapToRealPropertyDto(item)));
        return realPropertyRequestDtoList;
    }

    public void deleteById(Long id) {
        realPropertyRepository.deleteById(id);
    }

    public void update(Long id, RealProperty var0) {
        RealProperty var1 = realPropertyRepository.getOne(id);
        BeanUtils.copyProperties(var0, var1);
        realPropertyRepository.save(var1);
    }

    public void save(RealProperty realProperty) {
        realPropertyRepository.save(realProperty);
    }

    public void addFilesToProperty(Long propertyId, List<String> photoIds, List<String> housingPlans, List<String> virtualTours) {
        RealProperty realProperty = realPropertyRepository.getOne(propertyId);
        Map<RealPropertyFileType, Set<String>> filesMap = realProperty.getFilesMap();
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.PHOTO)) {
                filesMap.get(RealPropertyFileType.PHOTO).addAll(photoIds);
            } else {
                filesMap.put(RealPropertyFileType.PHOTO, new HashSet<>(photoIds));
            }
        }
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.HOUSING_PLAN)) {
                filesMap.get(RealPropertyFileType.HOUSING_PLAN).addAll(housingPlans);
            } else {
                filesMap.put(RealPropertyFileType.HOUSING_PLAN, new HashSet<>(housingPlans));
            }
        }
        if (!CollectionUtils.isEmpty(photoIds)) {
            if (filesMap.containsKey(RealPropertyFileType.VIRTUAL_TOUR)) {
                filesMap.get(RealPropertyFileType.VIRTUAL_TOUR).addAll(virtualTours);
            } else {
                filesMap.put(RealPropertyFileType.VIRTUAL_TOUR, new HashSet<>(virtualTours));
            }
        }
        realPropertyRepository.save(realProperty);
    }

    public RealPropertyRequestDto mapToRealPropertyDto(RealProperty realProperty) {
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
//                .parkingTypeIds(generalCharacteristics.getParkingTypes())
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
}
