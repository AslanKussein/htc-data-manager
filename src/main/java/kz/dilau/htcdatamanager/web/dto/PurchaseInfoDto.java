package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель параметров по операции Покупка")
public class PurchaseInfoDto {
    @ApiModelProperty(name = "objectPricePeriod", value = "Цена объекта от и до(млн тг)")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(name = "numberOfFloorsPeriod", value = "Этажность дома от и до")
    private IntegerPeriod numberOfFloorsPeriod;
    @ApiModelProperty(name = "floorPeriod", value = "Этаж от и до")
    private IntegerPeriod floorPeriod;
    @ApiModelProperty(name = "numberOfRoomsPeriod", value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(name = "totalAreaPeriod", value = "Общая площадь от и до")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(name = "livingAreaPeriod", value = "Жилая площадь от и до")
    private BigDecimalPeriod livingAreaPeriod;
    @ApiModelProperty(name = "kitchenAreaPeriod", value = "Площадь кухни от и до")
    private BigDecimalPeriod kitchenAreaPeriod;
    @ApiModelProperty(name = "balconyAreaPeriod", value = "Площадь балкона от и до")
    private BigDecimalPeriod balconyAreaPeriod;
    @ApiModelProperty(name = "ceilingHeightPeriod", value = "Высота потолков от и до")
    private BigDecimalPeriod ceilingHeightPeriod;
    @ApiModelProperty(name = "numberOfBedroomsPeriod", value = "Количество спален от и до")
    private IntegerPeriod numberOfBedroomsPeriod;
    @ApiModelProperty(name = "landAreaPeriod", value = "Площадь участка от и до")
    private BigDecimalPeriod landAreaPeriod;

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
