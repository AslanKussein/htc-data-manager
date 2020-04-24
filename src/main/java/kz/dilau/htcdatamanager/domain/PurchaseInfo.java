package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "purchase_info")
public class PurchaseInfo extends BaseEntity<Long> {
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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private RealProperty realProperty;

    public void setObjectPrice(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.objectPriceFrom = period.getFrom();
            this.objectPriceTo = period.getTo();
        }
    }

    public void setFloor(IntegerPeriod period) {
        if (nonNull(period)) {
            this.floorFrom = period.getFrom();
            this.floorTo = period.getTo();
        }
    }

    public void setNumberOfRooms(IntegerPeriod period) {
        if (nonNull(period)) {
            this.numberOfRoomsFrom = period.getFrom();
            this.numberOfRoomsTo = period.getTo();
        }
    }

    public void setNumberOfFloors(IntegerPeriod period) {
        if (nonNull(period)) {
            this.numberOfFloorsFrom = period.getFrom();
            this.numberOfFloorsTo = period.getTo();
        }
    }

    public void setNumberOfBedrooms(IntegerPeriod period) {
        if (nonNull(period)) {
            this.numberOfBedroomsFrom = period.getFrom();
            this.numberOfBedroomsTo= period.getTo();
        }
    }

    public void setTotalArea(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.totalAreaFrom = period.getFrom();
            this.totalAreaTo = period.getTo();
        }
    }

    public void setLivingArea(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.livingAreaFrom = period.getFrom();
            this.livingAreaTo = period.getTo();
        }
    }

    public void setKitchenArea(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.kitchenAreaFrom = period.getFrom();
            this.kitchenAreaTo = period.getTo();
        }
    }

    public void setBalconyArea(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.balconyAreaFrom = period.getFrom();
            this.balconyAreaTo = period.getTo();
        }
    }

    public void setCeilingHeight(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.ceilingHeightFrom = period.getFrom();
            this.ceilingHeightTo = period.getTo();
        }
    }

    public void setLandArea(BigDecimalPeriod period) {
        if (nonNull(period)) {
            this.landAreaFrom = period.getFrom();
            this.landAreaTo = period.getTo();
        }
    }
}
