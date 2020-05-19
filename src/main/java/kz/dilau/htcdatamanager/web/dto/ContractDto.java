package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractDto", description = "Идентификационные данные сделки")
public class ContractDto {
    @ApiModelProperty(value = "Срок действия договора")
    private Date contractPeriod;
    @ApiModelProperty(value = "Номер договора")
    private String contractNumber;
    @ApiModelProperty(value = "Сумма по договору")
    private BigDecimal amount;
    @ApiModelProperty(value = "Комиссия включена в стоимость")
    private boolean isCommissionIncludedInThePrice = false;
}
