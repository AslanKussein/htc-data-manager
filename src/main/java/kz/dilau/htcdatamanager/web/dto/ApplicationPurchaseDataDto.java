package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationPurchaseData;
import kz.dilau.htcdatamanager.domain.IdItem;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import lombok.*;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationPurchaseDataDto", description = "Общая информация о сделке покупки объекта")
public class ApplicationPurchaseDataDto extends AApplicationDataDto {
    @ApiModelProperty(value = "Цена объекта от и до(млн тг)")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "ID города", required = true)
    private Long cityId;
    @ApiModelProperty(value = "ID района")
    private Long districtId;

    public ApplicationPurchaseDataDto(ApplicationPurchaseData purchaseData) {
        if (nonNull(purchaseData.getPurchaseInfo())) {
            this.objectPricePeriod = new BigDecimalPeriod(purchaseData.getPurchaseInfo().getObjectPriceFrom(), purchaseData.getPurchaseInfo().getObjectPriceTo());
        }
        this.mortgage = purchaseData.getMortgage();
        this.probabilityOfBidding = purchaseData.getProbabilityOfBidding();
        this.theSizeOfTrades = purchaseData.getTheSizeOfTrades();
        this.possibleReasonForBiddingIdList = purchaseData.getPossibleReasonsForBidding()
                .stream()
                .map(IdItem::getId)
                .collect(Collectors.toList());
        this.note = purchaseData.getNote();
        this.cityId = purchaseData.getCityId();
        this.districtId = purchaseData.getDistrictId();
    }
}
