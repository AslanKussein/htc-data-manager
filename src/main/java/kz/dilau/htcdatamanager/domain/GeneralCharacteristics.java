package kz.dilau.htcdatamanager.domain;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.dictionary.PropertyDeveloper;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "general_characteristics")
public class GeneralCharacteristics extends AGeneralCharacteristics {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_developer_id")
    private PropertyDeveloper propertyDeveloper;
    @Column(name = "property_developer_id", insertable = false, updatable = false)
    private Long propertyDeveloperId;
    @Column(name = "housing_class")
    private String housingClass;
    @Column(name = "year_of_construction")
    private Integer yearOfConstruction;
    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
    @Column(name = "number_of_apartments")
    private Integer numberOfApartments;
    @Column(name = "apartments_on_the_site")
    private Integer apartmentsOnTheSite;
    //    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = TABLE_NAME_PREFIX + "general_characteristics_parking_type",
//            joinColumns = @JoinColumn(name = "general_characteristics_id"),
//            inverseJoinColumns = @JoinColumn(name = "parking_type_id")
//    )
    @Type(type = "jsonb")
    @Column(name = "parking_types", columnDefinition = "jsonb")
    private Set<IdItem> parkingTypes = new HashSet<>();
    //    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = TABLE_NAME_PREFIX + "general_characteristics_type_of_elevator",
//            joinColumns = @JoinColumn(name = "general_characteristics_id"),
//            inverseJoinColumns = @JoinColumn(name = "type_of_elevator_id")
//    )
    @Type(type = "jsonb")
    @Column(name = "types_of_elevator", columnDefinition = "jsonb")
    private Set<IdItem> typesOfElevator = new HashSet<>();

    public Set<IdItem> getParkingTypes() {
        if (isNull(parkingTypes)) {
            parkingTypes = new HashSet<>();
        }
        return parkingTypes;
    }

    public Set<IdItem> getTypesOfElevator() {
        if (isNull(typesOfElevator)) {
            typesOfElevator = new HashSet<>();
        }
        return typesOfElevator;
    }
}
