package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель параметров по операции Покупка КП")
public class PurchaseInfoClientDto {
    @ApiModelProperty(name = "objectPricePeriod", value = "Цена объекта от и до(млн тг)")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(name = "totalAreaPeriod", value = "Общая площадь от и до")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(name = "floorPeriod", value = "Этаж от и до")
    private IntegerPeriod floorPeriod;


    public PurchaseInfoClientDto(PurchaseInfo info) {
        this.floorPeriod = new IntegerPeriod(info.getFloorFrom(), info.getFloorTo());
        this.totalAreaPeriod = new BigDecimalPeriod(info.getTotalAreaFrom(), info.getTotalAreaTo());
        this.objectPricePeriod = new BigDecimalPeriod(info.getObjectPriceFrom(), info.getObjectPriceTo());

    }
}
