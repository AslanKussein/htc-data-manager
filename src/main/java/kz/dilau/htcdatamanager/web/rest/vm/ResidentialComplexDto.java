package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.domain.enums.YardType;
import lombok.Data;

@Data
public class ResidentialComplexDto {
    private Long id;
    private Long countryId;
    private String houseName;
    private Long propertyDeveloperId;
    private Integer numberOfEntrances;
    private Integer numberOfApartments;
    private String housingClass;
    private String housingCondition;
    private String apartmentsOnTheSite;

    private Integer ceilingHeight;
    private Boolean concierge;

    private Long districtId;

    private Integer houseNumber;
    private String houseNumberFraction;
    private Long materialOfConstructionId;
    private Integer numberOfFloors;

    private Long parkingTypeId;
    private Boolean playground;
    private Long streetId;

    private String typeOfElevator;
    private Boolean wheelchair;
    private YardType yardType;
    private Integer yearOfConstruction;








}
