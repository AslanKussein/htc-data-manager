package kz.dilau.htcdatamanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "purchase_info")
public class PurchaseInfo {
    @Id
    @Column(name = "id")
    private Long id;
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
    private Double totalAreaFrom;
    @Column(name = "total_area_to")
    private Double totalAreaTo;
    @Column(name = "living_area_from")
    private Double livingAreaFrom;
    @Column(name = "living_area_to")
    private Double livingAreaTo;
    @Column(name = "kitchen_area_from")
    private Double kitchenAreaFrom;
    @Column(name = "kitchen_area_to")
    private Double kitchenAreaTo;
    @Column(name = "balcony_area_from")
    private Double balconyAreaFrom;
    @Column(name = "balcony_area_to")
    private Double balconyAreaTo;
    @Column(name = "ceiling_height_from")
    private Double ceilingHeightFrom;
    @Column(name = "ceiling_height_to")
    private Double ceilingHeightTo;
    @Column(name = "number_of_bedrooms_from")
    private Integer numberOfBedroomsFrom;
    @Column(name = "number_of_bedrooms_to")
    private Integer numberOfBedroomsTo;
    @Column(name = "land_area_from")
    private Double landAreaFrom;
    @Column(name = "land_area_to")
    private Double landAreaTo;
    @Column(name = "number_of_floors_from")
    private Integer numberOfFloorsFrom;
    @Column(name = "number_of_floors_to")
    private Integer numberOfFloorsTo;
}
