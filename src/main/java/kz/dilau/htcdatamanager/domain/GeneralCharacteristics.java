package kz.dilau.htcdatamanager.domain;


import kz.dilau.htcdatamanager.domain.dictionary.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "general_characteristics")
public class GeneralCharacteristics {
    @Id
    @Column(name = "id")
    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
//    private Country country;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false)
    private District district;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "street_id", referencedColumnName = "id", nullable = false)
    private Street street;
    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;
    @Column(name = "house_number_fraction")
    private String houseNumberFraction;
    @Column(name = "ceiling_height", nullable = false)
    private Double ceilingHeight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id", referencedColumnName = "id")
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
    @JoinColumn(name = "material_of_construction_id", referencedColumnName = "id")
    private MaterialOfConstruction materialOfConstruction;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = DICTIONARY_TABLE_NAME_PREFIX + "general_characteristics_type_of_elevator",
            joinColumns = @JoinColumn(name = "general_characteristics_id"),
            inverseJoinColumns = @JoinColumn(name = "type_of_elevator_id")
    )
    private Set<TypeOfElevator> typesOfElevator = new HashSet<>();
    @Column(name = "concierge")
    private Boolean concierge;
    @Column(name = "wheelchair")
    private Boolean wheelchair;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_type_id", referencedColumnName = "id")
    private ParkingType parkingType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_type_id", referencedColumnName = "id")
    private YardType yardType;
    @Column(name = "playground")
    private Boolean playground;
}
