package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationDeposit;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DepositFormDto", description = "Модель формирования договора аванса/задатка")
public class DepositFormDto {
    @ApiModelProperty(value = "ID заявки")
    private Long applicationId;
    @ApiModelProperty(value = "ID заявки на продажу")
    private Long sellApplicationId;
    @ApiModelProperty(value = "Сумма")
    private BigDecimal payedSum;
    @ApiModelProperty(value = "Тип договора")
    private Long payTypeId;

    public DepositFormDto(ApplicationDeposit deposit) {
        if (nonNull(deposit)) {
            this.applicationId = deposit.getApplicationId();
            this.sellApplicationId = deposit.getSellApplicationId();
            this.payedSum = deposit.getPayedSum();
            this.payTypeId = deposit.getPayTypeId();
        }
    }
}
