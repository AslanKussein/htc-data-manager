package kz.dilau.htcdatamanager.web.rest.vm;

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
}
