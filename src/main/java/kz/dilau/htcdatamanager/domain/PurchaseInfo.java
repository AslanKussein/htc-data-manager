package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = TABLE_NAME_PREFIX + "purchase_info")
public class PurchaseInfo extends AGeneralCharacteristics {
    @Column(name = "object_price_from")
    private BigDecimal objectPriceFrom;
    @Column(name = "object_price_to")
    private BigDecimal objectPriceTo;
    @Column(name = "floor_from")
    private Integer floorFrom;
    @Column(name = "floor_to")
    private Integer floorTo;
    @Column(name = "number_of_rooms_from")
    private Integer numberOfRoomsFrom;
    @Column(name = "number_of_rooms_to")
    private Integer numberOfRoomsTo;
    @Column(name = "total_area_from")
    private BigDecimal totalAreaFrom;
    @Column(name = "total_area_to")
    private BigDecimal totalAreaTo;
    @Column(name = "living_area_from")
    private BigDecimal livingAreaFrom;
    @Column(name = "living_area_to")
    private BigDecimal livingAreaTo;
    @Column(name = "kitchen_area_from")
    private BigDecimal kitchenAreaFrom;
    @Column(name = "kitchen_area_to")
    private BigDecimal kitchenAreaTo;
    @Column(name = "balcony_area_from")
    private BigDecimal balconyAreaFrom;
    @Column(name = "balcony_area_to")
    private BigDecimal balconyAreaTo;
    @Column(name = "ceiling_height_from")
    private BigDecimal ceilingHeightFrom;
    @Column(name = "ceiling_height_to")
    private BigDecimal ceilingHeightTo;
    @Column(name = "number_of_bedrooms_from")
    private Integer numberOfBedroomsFrom;
    @Column(name = "number_of_bedrooms_to")
    private Integer numberOfBedroomsTo;
    @Column(name = "land_area_from")
    private BigDecimal landAreaFrom;
    @Column(name = "land_area_to")
    private BigDecimal landAreaTo;
    @Column(name = "number_of_floors_from")
    private Integer numberOfFloorsFrom;
    @Column(name = "number_of_floors_to")
    private Integer numberOfFloorsTo;
    @Column(name = "year_of_construction_from")
    private Integer yearOfConstructionFrom;
    @Column(name = "year_of_construction_to")
    private Integer yearOfConstructionTo;
    @Column(name = "apartments_on_the_site_from")
    private Integer apartmentsOnTheSiteFrom;
    @Column(name = "apartments_on_the_site_to")
    private Integer apartmentsOnTheSiteTo;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = TABLE_NAME_PREFIX + "purchase_info_parking_type",
//            joinColumns = @JoinColumn(name = "purchase_info_id"),
//            inverseJoinColumns = @JoinColumn(name = "parking_type_id")
//    )
    @Type(type = "jsonb")
    @Column(name = "parking_types", columnDefinition = "jsonb")
    private Set<IdItem> parkingTypes = new HashSet<>();
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = TABLE_NAME_PREFIX + "purchase_info_type_of_elevator",
//            joinColumns = @JoinColumn(name = "purchase_info_id"),
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
