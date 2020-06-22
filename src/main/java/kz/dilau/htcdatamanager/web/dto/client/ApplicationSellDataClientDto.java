package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationSellData;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationSellDataClientDto", description = "Общая информация о сделке продажи объекта")
public class ApplicationSellDataClientDto {
    @ApiModelProperty(value = "Вероятность торга")
    protected Boolean probabilityOfBidding;
    @ApiModelProperty(value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(value = "Полный адрес (API КазПочты)")
    private RealPropertyClientDto realPropertyClientDto;
    @ApiModelProperty(value = "Комментарий")
    private String note;

    public ApplicationSellDataClientDto(ApplicationSellData sellData) {
        if (nonNull(sellData)) {
            this.objectPrice = sellData.getObjectPrice();
            this.probabilityOfBidding = sellData.getProbabilityOfBidding();
            this.note = sellData.getNote();

            if (nonNull(sellData.getRealProperty()) && nonNull(sellData.getApplication())) {
                this.realPropertyClientDto = new RealPropertyClientDto(sellData.getRealProperty());
            }
        }
    }
}
