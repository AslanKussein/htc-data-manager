package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.enums.YardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "residential_complex")
public class ResidentialComplex extends AuditableBaseEntity<String, Long> {
    //    @NotNull(message = "Country must not be null")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ManyToOne(optional = false)
//    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;


    @Column(name = "house_name")
    private String houseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id", referencedColumnName = "id")
    private PropertyDeveloper propertyDeveloper;

    //    @Min(1)
    @Column(name = "number_of_entrances")
    private Integer numberOfEntrances;
    //    @Min(1)
    @Column(name = "number_of_apartments")
    private Integer numberOfApartments;

    @Column(name = "housing_class")
    private String housingClass;
    @Column(name = "housing_condition")
    private String housingCondition;

    @ManyToOne
    @JoinColumn(name = "street_id", referencedColumnName = "id")
    private Street street;

    private Integer houseNumber;
    private String houseNumberFraction;
    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    private Integer yearOfConstruction;


    @ManyToOne
    @JoinColumn(name = "material_of_construction_id", referencedColumnName = "id")
    private MaterialOfConstruction materialOfConstruction;

    private Integer ceilingHeight;

    private Integer numberOfFloors;

    private String apartmentsOnTheSite;

    private String typeOfElevator;

    private Boolean concierge;

    private Boolean wheelchair;

    private YardType yardType;


    @ManyToOne
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private ParkingType parkingType;

    private Boolean playground;






}
