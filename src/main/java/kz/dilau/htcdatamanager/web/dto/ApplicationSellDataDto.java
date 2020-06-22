package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationSellData;
import kz.dilau.htcdatamanager.domain.IdItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationSellDataDto", description = "Общая информация о сделке продажи объекта")
public class ApplicationSellDataDto extends AApplicationDataDto {
    @ApiModelProperty(value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(value = "Обременение")
    private Boolean encumbrance;//обременение
    @ApiModelProperty(value = "Общая долевая собственность")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @ApiModelProperty(value = "Обмен")
    private Boolean exchange;//обмен
    @ApiModelProperty(value = "Описание")
    private String description;

    private Long applicationId;

    public ApplicationSellDataDto(ApplicationSellData sellData) {
        this.objectPrice = sellData.getObjectPrice();
        this.encumbrance = sellData.getEncumbrance();
        this.sharedOwnershipProperty = sellData.getSharedOwnershipProperty();
        this.exchange = sellData.getExchange();
        this.description = sellData.getDescription();
        this.mortgage = sellData.getMortgage();
        this.probabilityOfBidding = sellData.getProbabilityOfBidding();
        this.theSizeOfTrades = sellData.getTheSizeOfTrades();
        this.note = sellData.getNote();
        if (!sellData.getPossibleReasonsForBidding().isEmpty()) {
            this.possibleReasonForBiddingIdList = sellData.getPossibleReasonsForBidding()
                    .stream()
                    .map(IdItem::getId)
                    .collect(Collectors.toList());
        }
        if (!sellData.getApplicationFlags().isEmpty()) {
            this.applicationFlagIdList = sellData.getApplicationFlags()
                    .stream()
                    .map(IdItem::getId)
                    .collect(Collectors.toList());
        }

        this.applicationId = sellData.getApplication().getId();
    }
}
