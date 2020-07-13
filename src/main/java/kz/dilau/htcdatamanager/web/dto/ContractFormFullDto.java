package kz.dilau.htcdatamanager.web.dto;

import kz.dilau.htcdatamanager.domain.ApplicationContract;
import lombok.*;

import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractFormFullDto extends ContractFormDto {

    protected ZonedDateTime createdDate;

    public ContractFormFullDto(ApplicationContract contract) {
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
