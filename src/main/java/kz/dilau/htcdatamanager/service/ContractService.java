package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.config.CommissionRange;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

public interface ContractService {
    ContractFormDto getContractForm(String token, Long applicationId);

    String generateContract(ContractFormDto dto);

    String generateContractHandsel(ContractFormDto dto);

    String generateContractAvans(ContractFormDto dto);

    Long missContract(ContractFormDto dto);

    Integer getCommission(Integer sum, Long objectTypeId);

    ListResponse<CommissionRange> getAllCommissions();
}
