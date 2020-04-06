package kz.dilau.htcdatamanager.domain;


import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME_PREFIX + "info")
public class Info extends BaseEntity<Long> {
    @Column(name = "apartments_on_the_site")
    private String apartmentsOnTheSite;
    @Min(1)
    @Column(name = "ceiling_height")
    private Integer ceilingHeight;
    //    @NotNull(message = "City must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
    @Column(name = "concierge")
    private Boolean concierge;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
    @NotNull(message = "House number must not be null")
    @Min(1)
    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;
    @Column(name = "house_number_fraction")
    private String houseNumberFraction;
    @Column(name = "material_of_construction")
    private String materialOfConstruction;
    @Min(1)
    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private ParkingType parkingType;
    @Column(name = "playground")
    private Boolean playground;
    //    @NotNull(message = "Street must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "street_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "street_id", referencedColumnName = "id")
    private Street street;
    @Column(name = "type_of_elevator")
    private String typeOfElevator;
    @Column(name = "wheelchair")
    private Boolean wheelchair;
    @Column(name = "yard_type")
    @Enumerated(EnumType.STRING)
    private YardType yardType;
    @Column(name = "year_of_construction")
    private Integer yearOfConstruction;
}
