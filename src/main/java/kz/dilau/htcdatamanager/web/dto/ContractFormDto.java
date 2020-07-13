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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractFormDto", description = "Модель формирования договора ОУ")
public class ContractFormDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    protected Long applicationId;
    @ApiModelProperty(value = "Срок действия договора", required = true)
    protected ZonedDateTime contractPeriod;
    @ApiModelProperty(value = "Номер договора")
    protected String contractNumber;
    @ApiModelProperty(value = "Сумма по договору", required = true)
    protected BigDecimal contractSum;
    @ApiModelProperty(value = "Комиссия", required = true)
    protected BigDecimal commission;
    @ApiModelProperty(value = "Тип договора")
    protected Long contractTypeId;
    @ApiModelProperty(value = "Идентификатор прикрепленного документа")
    protected String guid;

    public ContractFormDto(ApplicationContract contract) {
        if (nonNull(contract)) {
            this.applicationId = contract.getApplicationId();
            this.contractPeriod = contract.getContractPeriod();
            this.contractNumber = contract.getContractNumber();
            this.contractSum = contract.getContractSum();
            this.commission = contract.getCommission();
            this.contractTypeId = contract.getContractTypeId();
            this.guid = contract.getGuid();
        }
    }
}
