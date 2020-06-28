package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.config.CommissionRange;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CommissionRangeDto", description = "Модель комиссий")
public class CommissionRangeDto {
    @ApiModelProperty(value = "Сумма договора ОТ")
    private long from;
    @ApiModelProperty(value = "Сумма договора ДО")
    private long to;
    @ApiModelProperty(value = "Сумма комиссии")
    private BigDecimal amount;
    @ApiModelProperty(value = "Процент комиссии за дом")
    private BigDecimal houseAmount;

    public CommissionRangeDto(CommissionRange range) {
        this.from = range.getFrom();
        this.to = range.getTo();
        this.amount = range.getAmount();
    }
}
