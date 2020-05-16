package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import kz.dilau.htcdatamanager.domain.dictionary.YardType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@MappedSuperclass
public abstract class AGeneralCharacteristics extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_of_construction_id")
    private MaterialOfConstruction materialOfConstruction;
    @Column(name = "material_of_construction_id", insertable = false, updatable = false)
    private Long materialOfConstructionId;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = TABLE_NAME_PREFIX + "new_general_characteristics_parking_type",
            joinColumns = @JoinColumn(name = "general_characteristics_id"),
            inverseJoinColumns = @JoinColumn(name = "parking_type_id")
    )
    private Set<ParkingType> parkingTypes = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_type_id")
    private YardType yardType;
    @Column(name = "yard_type_id", insertable = false, updatable = false)
    private Long yardTypeId;
    @Column(name = "playground")
    private Boolean playground;

    public Set<TypeOfElevator> getTypesOfElevator() {
        if (isNull(typesOfElevator)) {
            typesOfElevator = new HashSet<>();
        }
        return typesOfElevator;
    }

    public Set<ParkingType> getParkingTypes() {
        if (isNull(parkingTypes)) {
            parkingTypes = new HashSet<>();
        }
        return parkingTypes;
    }
}
