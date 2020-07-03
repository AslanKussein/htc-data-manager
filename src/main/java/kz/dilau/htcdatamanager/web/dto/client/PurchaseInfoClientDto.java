package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationPurchaseData;
import kz.dilau.htcdatamanager.domain.PurchaseInfo;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель параметров по операции Покупка КП")
public class PurchaseInfoClientDto {


    @ApiModelProperty(value = "Студия")
    protected Boolean atelier;
    @ApiModelProperty(value = "Санузел раздельный")
    protected Boolean separateBathroom;
    @ApiModelProperty(value = "Вероятность торга")
    protected Boolean probabilityOfBidding;
    @ApiModelProperty(value = "Комментарий")
    protected String note;
    private Long dataId;
    @ApiModelProperty(value = "ID города", required = true)
    private Long cityId;
    @ApiModelProperty(value = "ID района")
    private Long districtId;
    @ApiModelProperty(name = "objectPricePeriod", value = "Цена объекта от и до(млн тг)")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(value = "Количество комнат для  создания заявки из КП")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "totalAreaPeriod", value = "Общая площадь от и до")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(name = "floorPeriod", value = "Этаж от и до")
    private IntegerPeriod floorPeriod;
    @ApiModelProperty(name = "mortgage", value = "Покупка через ипотеку")
    private Boolean mortgage;

    public PurchaseInfoClientDto(ApplicationPurchaseData data) {
        if (nonNull(data)) {
            this.dataId = data.getId();
            PurchaseInfo info = data.getPurchaseInfo();
            if (nonNull(info)) {
                this.floorPeriod = new IntegerPeriod(info.getFloorFrom(), info.getFloorTo());
                this.totalAreaPeriod = new BigDecimalPeriod(info.getTotalAreaFrom(), info.getTotalAreaTo());
                this.objectPricePeriod = new BigDecimalPeriod(info.getObjectPriceFrom(), info.getObjectPriceTo());
                this.atelier = info.getAtelier();
                this.separateBathroom = info.getSeparateBathroom();
                this.numberOfRoomsPeriod = new IntegerPeriod(info.getNumberOfRoomsFrom(), info.getNumberOfRoomsTo());
            }
            this.probabilityOfBidding = data.getProbabilityOfBidding();
            this.cityId = data.getCityId();
            this.districtId = data.getDistrictId();
            this.mortgage = data.getMortgage();
            this.note = data.getNote();
        }
    }
}
    