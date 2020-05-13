package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.RealPropertyClientService;
import kz.dilau.htcdatamanager.service.RealPropertyService;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationClientViewDto;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.util.PeriodUtils.mapToBigDecimalPeriod;
import static kz.dilau.htcdatamanager.util.PeriodUtils.mapToIntegerPeriod;

@RequiredArgsConstructor
@Service
public class RealPropertyClientServiceImpl implements RealPropertyClientService {
    private final ApplicationService applicationService;
    private final DictionaryCacheService cacheService;
    private final RealPropertyService realPropertyService;

    @Override
    public ApplicationClientViewDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationClientDto(application);
    }

    private ApplicationClientViewDto mapToApplicationClientDto(Application application) {
        return ApplicationClientViewDto.builder()
                .id(application.getId())
                .clientDto(mapToClientDto(application.getClient()))
                .realPropertyRequestDto(nonNull(application.getRealProperty()) ? mapToRealPropertyClientViewDto(application.getRealProperty()) : null)
                .operationTypeId(application.getOperationType().getId())
                .objectPrice(application.getObjectPrice())
                .mortgage(application.getMortgage())
                .encumbrance(application.getEncumbrance())
                .sharedOwnershipProperty(application.getSharedOwnershipProperty())
                .exchange(application.getExchange())
                .probabilityOfBidding(application.getProbabilityOfBidding())
                .theSizeOfTrades(application.getTheSizeOfTrades())
                .possibleReasonForBiddingIdList(application.getPossibleReasonsForBidding().stream()
                        .map(PossibleReasonForBidding::getId)
                        .collect(Collectors.toList()))
                .contractPeriod(application.getContractPeriod())
                .contractNumber(application.getContractNumber())
                .amount(application.getAmount())
                .isCommissionIncludedInThePrice(application.isCommissionIncludedInThePrice())
                .note(application.getNote())
                .agent(application.getCurrentAgent())
                .build();
    }

    private ClientDto mapToClientDto(Client client) {
        return new ClientDto(client);
    }


    private <T extends BaseCustomDictionary> DictionaryDto getDicById(Class<T> clazz, Long id) {
        if (id == null) {
            return null;
        }
        return convertInstanceOfObject(cacheService.getById(clazz, id), DictionaryDto.class);
    }

    public <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public RealPropertyClientViewDto mapToRealPropertyClientViewDto(RealProperty realProperty) {
        GeneralCharacteristics generalCharacteristics = nonNull(realProperty.getResidentialComplex()) ? realProperty.getResidentialComplex().getGeneralCharacteristics() : realProperty.getGeneralCharacteristics();
        return RealPropertyClientViewDto.builder()
                .objectType(getDicById(ObjectType.class, realProperty.getObjectTypeId()))
                .city(getDicById(City.class, generalCharacteristics.getCityId()))
                .district(getDicById(District.class, generalCharacteristics.getDistrictId()))
                .street(getDicById(Street.class, generalCharacteristics.getStreetId()))
                .houseNumber(generalCharacteristics.getHouseNumber())
                .houseNumberFraction(generalCharacteristics.getHouseNumberFraction())
                .floor(realProperty.getFloor())
                .residentialComplex(new ResidentialComplexDto(realProperty.getResidentialComplex()))
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
                .materialOfConstruction(getDicById(MaterialOfConstruction.class, generalCharacteristics.getMaterialOfConstructionId()))
                .yearOfConstruction(generalCharacteristics.getYearOfConstruction())
                .concierge(generalCharacteristics.getConcierge())
                .wheelchair(generalCharacteristics.getWheelchair())
                .yardType(getDicById(YardType.class, generalCharacteristics.getYardTypeId()))
                .playground(generalCharacteristics.getPlayground())
                .typeOfElevatorList(generalCharacteristics.getTypesOfElevator().stream().map(TypeOfElevator::getId).collect(Collectors.toList()))
                //.parkingTypeList(generalCharacteristics.getParkingTypes())
                .propertyDeveloper(getDicById(PropertyDeveloper.class, generalCharacteristics.getPropertyDeveloperId()))
                .housingClass(generalCharacteristics.getHousingClass())
                .housingCondition(generalCharacteristics.getHousingCondition())
                .sewerage(getDicById(Sewerage.class, realProperty.getSewerageId()))
                .heatingSystem(getDicById(HeatingSystem.class, realProperty.getHeatingSystemId()))
                .numberOfApartments(generalCharacteristics.getNumberOfApartments())
                .landArea(realProperty.getLandArea())
                .purchaseInfoDto(realPropertyService.mapToPurchaseInfoDto(realProperty.getPurchaseInfo()))
                .photoIdList(realPropertyService.mapPhotoList(realProperty, RealPropertyFileType.PHOTO))
                .housingPlanImageIdList(realPropertyService.mapPhotoList(realProperty, RealPropertyFileType.HOUSING_PLAN))
                .virtualTourImageIdList(realPropertyService.mapPhotoList(realProperty, RealPropertyFileType.VIRTUAL_TOUR))
                .latitude(realProperty.getLatitude())
                .longitude(realProperty.getLongitude())
                .build();
    }



}
