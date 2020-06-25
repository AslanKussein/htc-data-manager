package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.IdItem;
import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.*;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PurchaseInfoDto", description = "Общая информация об объекте покупки")
public class PurchaseInfoDto extends AGeneralCharacteristicsDto {
    @ApiModelProperty(value = "Этажность дома от и до")
    private IntegerPeriod numberOfFloorsPeriod;
    @ApiModelProperty(value = "Этаж от и до")
    private IntegerPeriod floorPeriod;
    @ApiModelProperty(value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(value = "Общая площадь от и до")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(value = "Жилая площадь от и до")
    private BigDecimalPeriod livingAreaPeriod;
    @ApiModelProperty(value = "Площадь кухни от и до")
    private BigDecimalPeriod kitchenAreaPeriod;
    @ApiModelProperty(value = "Площадь балкона от и до")
    private BigDecimalPeriod balconyAreaPeriod;
    @ApiModelProperty(value = "Высота потолков от и до")
    private BigDecimalPeriod ceilingHeightPeriod;
    @ApiModelProperty(value = "Количество спален от и до")
    private IntegerPeriod numberOfBedroomsPeriod;
    @ApiModelProperty(value = "Площадь участка от и до")
    private BigDecimalPeriod landAreaPeriod;
    @ApiModelProperty(value = "Год постройки от и до")
    private IntegerPeriod yearOfConstructionPeriod;
    @ApiModelProperty(value = "Количество квартир на площадке от и до")
    private IntegerPeriod apartmentsOnTheSitePeriod;
    @ApiModelProperty(value = "Студия")
    protected Boolean atelier;
    @ApiModelProperty(value = "Санузел раздельный")
    protected Boolean separateBathroom;

    public PurchaseInfoDto(PurchaseInfo info) {
        if (nonNull(info)) {
            this.numberOfFloorsPeriod = new IntegerPeriod(info.getNumberOfFloorsFrom(), info.getNumberOfFloorsTo());
            this.floorPeriod = new IntegerPeriod(info.getFloorFrom(), info.getFloorTo());
            this.numberOfRoomsPeriod = new IntegerPeriod(info.getNumberOfRoomsFrom(), info.getNumberOfRoomsTo());
            this.totalAreaPeriod = new BigDecimalPeriod(info.getTotalAreaFrom(), info.getTotalAreaTo());
            this.livingAreaPeriod = new BigDecimalPeriod(info.getLivingAreaFrom(), info.getLivingAreaTo());
            this.kitchenAreaPeriod = new BigDecimalPeriod(info.getKitchenAreaFrom(), info.getKitchenAreaTo());
            this.balconyAreaPeriod = new BigDecimalPeriod(info.getBalconyAreaFrom(), info.getBalconyAreaTo());
            this.ceilingHeightPeriod = new BigDecimalPeriod(info.getCeilingHeightFrom(), info.getCeilingHeightTo());
            this.landAreaPeriod = new BigDecimalPeriod(info.getLandAreaFrom(), info.getLandAreaTo());
            this.numberOfBedroomsPeriod = new IntegerPeriod(info.getNumberOfBedroomsFrom(), info.getNumberOfBedroomsTo());
            this.yearOfConstructionPeriod = new IntegerPeriod(info.getYearOfConstructionFrom(), info.getYearOfConstructionTo());
            this.apartmentsOnTheSitePeriod = new IntegerPeriod(info.getApartmentsOnTheSiteFrom(), info.getApartmentsOnTheSiteTo());
            this.materialOfConstructionId = info.getMaterialOfConstructionId();
            this.concierge = info.getConcierge();
            this.wheelchair = info.getWheelchair();
            this.yardTypeId = info.getYardTypeId();
            this.playground = info.getPlayground();
            this.atelier = info.getAtelier();
            this.separateBathroom = info.getSeparateBathroom();
            this.typeOfElevatorList = info.getTypesOfElevator()
                    .stream()
                    .map(IdItem::getId)
                    .collect(Collectors.toList());
            this.parkingTypeIds = info.getParkingTypes()
                    .stream()
                    .map(IdItem::getId)
                    .collect(Collectors.toList());
        }
    }

//    public BigDecimalPeriod getObjectPricePeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return objectPricePeriod;
//    }
//
//    public IntegerPeriod getNumberOfFloorsPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return numberOfFloorsPeriod;
//    }
//
//    public IntegerPeriod getFloorPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return floorPeriod;
//    }
//
//    public IntegerPeriod getNumberOfRoomsPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return numberOfRoomsPeriod;
//    }
//
//    public BigDecimalPeriod getTotalAreaPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return totalAreaPeriod;
//    }
//
//    public BigDecimalPeriod getLivingAreaPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return livingAreaPeriod;
//    }
//
//    public BigDecimalPeriod getKitchenAreaPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return kitchenAreaPeriod;
//    }
//
//    public BigDecimalPeriod getBalconyAreaPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return balconyAreaPeriod;
//    }
//
//    public BigDecimalPeriod getCeilingHeightPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return ceilingHeightPeriod;
//    }
//
//    public IntegerPeriod getNumberOfBedroomsPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return numberOfBedroomsPeriod;
//    }
//
//    public BigDecimalPeriod getLandAreaPeriod() {
//        if (isNull(objectPricePeriod)) {
//            objectPricePeriod = new BigDecimalPeriod();
//        }
//        return landAreaPeriod;
//    }
}
