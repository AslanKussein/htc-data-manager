package kz.dilau.htcdatamanager.domain;


import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "general_characteristics")
public class GeneralCharacteristics extends BaseEntity<Long> {
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
//    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id")
    private Street street;
    @Column(name = "house_number")
    private Integer houseNumber;
    @Column(name = "house_number_fraction")
    private String houseNumberFraction;
    @Column(name = "ceiling_height")
    private BigDecimal ceilingHeight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id")
    private PropertyDeveloper propertyDeveloper;
    @Column(name = "housing_class")
    private String housingClass;
    @Column(name = "housing_condition")
    private String housingCondition;
    @Column(name = "year_of_construction")
    private Integer yearOfConstruction;
    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
    @Column(name = "number_of_apartments")
    private Integer numberOfApartments;
    @Column(name = "apartments_on_the_site")
    private String apartmentsOnTheSite;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_of_construction_id")
    private MaterialOfConstruction materialOfConstruction;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = TABLE_NAME_PREFIX + "general_characteristics_type_of_elevator",
            joinColumns = @JoinColumn(name = "general_characteristics_id"),
            inverseJoinColumns = @JoinColumn(name = "type_of_elevator_id")
    )
    private Set<TypeOfElevator> typesOfElevator = new HashSet<>();
    @Column(name = "concierge")
    private Boolean concierge;
    @Column(name = "wheelchair")
    private Boolean wheelchair;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_type_id")
    private ParkingType parkingType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_type_id")
    private YardType yardType;
    @Column(name = "playground")
    private Boolean playground;
}
