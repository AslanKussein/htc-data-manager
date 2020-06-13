package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.ApplicationContract;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractFormDto", description = "Модель формирования договора ОУ")
public class ContractFormDto {
    @ApiModelProperty(value = "ID заявки")
    private Long applicationId;
    @ApiModelProperty(value = "Срок действия договора")
    private ZonedDateTime contractPeriod;
    @ApiModelProperty(value = "Номер договора")
    private String contractNumber;
    @ApiModelProperty(value = "Сумма по договору")
    private BigDecimal contractSum;
    @ApiModelProperty(value = "Комиссия")
    private BigDecimal commission;
    @ApiModelProperty(value = "Признак эксклюзивного договора")
    private Boolean isExclusive = false;
    @ApiModelProperty(value = "Идентификатор прикрепленного документа")
    private String guid;

    public ContractFormDto(ApplicationContract contract) {
        if (nonNull(contract)) {
            this.applicationId = contract.getApplicationId();
            this.contractPeriod = contract.getContractPeriod();
            this.contractNumber = contract.getContractNumber();
            this.contractSum = contract.getContractSum();
            this.commission = contract.getCommission();
            this.isExclusive = contract.getIsExclusive();
            this.guid = contract.getGuid();
        }
    }
}
