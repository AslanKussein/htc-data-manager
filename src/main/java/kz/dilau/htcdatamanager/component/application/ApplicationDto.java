package kz.dilau.htcdatamanager.component.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.component.owner.RealPropertyOwnerDto;
import kz.dilau.htcdatamanager.component.property.RealPropertyRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Модель заявки", description = "Модель заявки")
public class ApplicationDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "Данные по клиенту", required = true)
    @NotNull(message = "Owner must not be null")
    private RealPropertyOwnerDto ownerDto;
    @ApiModelProperty(value = "Данные по невижимости", required = true)
    @NotNull(message = "Real property must not be null")
    private RealPropertyRequestDto realPropertyRequestDto;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(name = "objectPrice", value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(name = "objectPriceFrom", value = "Цена объекта от(млн тг)")
    private BigDecimal objectPriceFrom;
    @ApiModelProperty(name = "objectPriceTo", value = "Цена объекта до(млн тг)")
    private BigDecimal objectPriceTo;
    @ApiModelProperty(name = "mortgage", value = "Ипотека")
    private Boolean mortgage;//ипотека
    @ApiModelProperty(name = "encumbrance", value = "Обременение")
    private Boolean encumbrance;//обременение
    @ApiModelProperty(name = "sharedOwnershipProperty", value = "Общая долевая собственность")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @ApiModelProperty(name = "exchange", value = "Обмен")
    private Boolean exchange;//обмен
    @ApiModelProperty(name = "probabilityOfBidding", value = "Вероятность торга")
    private Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(name = "theSizeOfTrades", value = "Размер торга")
    private String theSizeOfTrades;//размер торга
    @ApiModelProperty(name = "possibleReasonForBiddingIdList", value = "ID возможных причин торга")
    private List<Long> possibleReasonForBiddingIdList;
    @ApiModelProperty(name = "contractPeriod", value = "Срок действия договора")
    private Date contractPeriod;
    @ApiModelProperty(name = "amount", value = "Сумма по договору")
    private BigDecimal amount;
    @ApiModelProperty(name = "isCommissionIncludedInThePrice", value = "Комиссия включена в стоимость")
    @NotNull(message = "Commission is included in the price must not be null")
    private boolean isCommissionIncludedInThePrice = false;
    @ApiModelProperty(name = "note", value = "Примечание")
    private String note;
}
