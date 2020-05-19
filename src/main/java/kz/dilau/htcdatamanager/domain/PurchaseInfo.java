package kz.dilau.htcdatamanager.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.domain.dictionary.YardType;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
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

    @Type(type = "jsonb")
    @Column(name = "parking_types", columnDefinition = "jsonb")
    private Set<IdItem> parkingTypes = new HashSet<>();
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

    public PurchaseInfo(PurchaseInfoDto dto, BigDecimalPeriod objectPrice, MaterialOfConstruction materialOfConstruction, YardType yardType) {
        if (nonNull(objectPrice)) {
            this.objectPriceFrom = objectPrice.getFrom();
            this.objectPriceTo = objectPrice.getTo();
        }
        if (nonNull(dto)) {
            this.id = dto.getId();
            if (nonNull(dto.getFloorPeriod())) {
                this.floorFrom = dto.getFloorPeriod().getFrom();
                this.floorTo = dto.getFloorPeriod().getTo();
            }
            if (nonNull(dto.getNumberOfFloorsPeriod())) {
                this.numberOfFloorsFrom = dto.getNumberOfFloorsPeriod().getFrom();
                this.numberOfFloorsTo = dto.getNumberOfFloorsPeriod().getTo();
            }
            if (nonNull(dto.getApartmentsOnTheSitePeriod())) {
                this.apartmentsOnTheSiteFrom = dto.getApartmentsOnTheSitePeriod().getFrom();
                this.apartmentsOnTheSiteTo = dto.getApartmentsOnTheSitePeriod().getTo();
            }
            if (nonNull(dto.getBalconyAreaPeriod())) {
                this.balconyAreaFrom = dto.getBalconyAreaPeriod().getFrom();
                this.balconyAreaTo = dto.getBalconyAreaPeriod().getTo();
            }
            if (nonNull(dto.getKitchenAreaPeriod())) {
                this.kitchenAreaFrom = dto.getKitchenAreaPeriod().getFrom();
                this.kitchenAreaTo = dto.getKitchenAreaPeriod().getTo();
            }
            if (nonNull(dto.getLivingAreaPeriod())) {
                this.livingAreaFrom = dto.getLivingAreaPeriod().getFrom();
                this.livingAreaTo = dto.getLivingAreaPeriod().getTo();
            }
            if (nonNull(dto.getTotalAreaPeriod())) {
                this.totalAreaFrom = dto.getTotalAreaPeriod().getFrom();
                this.totalAreaTo = dto.getTotalAreaPeriod().getTo();
            }
            if (nonNull(dto.getLandAreaPeriod())) {
                this.landAreaFrom = dto.getLandAreaPeriod().getFrom();
                this.landAreaTo = dto.getLandAreaPeriod().getTo();
            }
            if (nonNull(dto.getCeilingHeightPeriod())) {
                this.ceilingHeightFrom = dto.getCeilingHeightPeriod().getFrom();
                this.ceilingHeightTo = dto.getCeilingHeightPeriod().getTo();
            }
            if (nonNull(dto.getNumberOfRoomsPeriod())) {
                this.numberOfRoomsFrom = dto.getNumberOfRoomsPeriod().getFrom();
                this.numberOfRoomsTo = dto.getNumberOfRoomsPeriod().getTo();
            }
            if (nonNull(dto.getNumberOfBedroomsPeriod())) {
                this.numberOfBedroomsFrom = dto.getNumberOfBedroomsPeriod().getFrom();
                this.numberOfBedroomsTo = dto.getNumberOfBedroomsPeriod().getTo();
            }
            if (nonNull(dto.getYearOfConstructionPeriod())) {
                this.yearOfConstructionFrom = dto.getYearOfConstructionPeriod().getFrom();
                this.yearOfConstructionTo = dto.getYearOfConstructionPeriod().getTo();
            }
            if (nonNull(dto.getParkingTypeIds())) {
                this.parkingTypes = dto.getParkingTypeIds()
                        .stream()
                        .map(IdItem::new)
                        .collect(Collectors.toSet());
            }
            if (nonNull(dto.getTypeOfElevatorList())) {
                this.typesOfElevator = dto.getTypeOfElevatorList()
                        .stream()
                        .map(IdItem::new)
                        .collect(Collectors.toSet());
            }
            this.concierge = dto.getConcierge();
            this.playground = dto.getPlayground();
            this.wheelchair = dto.getWheelchair();
        }
        this.materialOfConstruction = materialOfConstruction;
        this.yardType = yardType;
    }
}
