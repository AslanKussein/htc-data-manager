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
@ApiModel(value = "ContractFormDto", description = "Модель формирования договора ОУ для сервиса уведомлений")
public class ContractFormForNotificationDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;
    @ApiModelProperty(value = "Срок действия договора", required = true)
    private ZonedDateTime contractPeriod;
    @ApiModelProperty(value = "Номер договора")
    private String contractNumber;
    @ApiModelProperty(value = "Сумма по договору", required = true)
    private BigDecimal contractSum;
    @ApiModelProperty(value = "Комиссия", required = true)
    private BigDecimal commission;
    @ApiModelProperty(value = "Тип договора")
    private Long contractTypeId;
    @ApiModelProperty(value = "Идентификатор прикрепленного документа")
    private String guid;
    protected ZonedDateTime createdDate;

    public ContractFormForNotificationDto(ApplicationContract contract) {
        if (nonNull(contract)) {
            this.applicationId = contract.getApplicationId();
            this.contractPeriod = contract.getContractPeriod();
            this.contractNumber = contract.getContractNumber();
            this.contractSum = contract.getContractSum();
            this.commission = contract.getCommission();
            this.contractTypeId = contract.getContractTypeId();
            this.guid = contract.getGuid();
            this.createdDate = contract.getCreatedDate();
        }
    }
}
