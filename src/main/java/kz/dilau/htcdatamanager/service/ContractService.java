package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ContractFormDto;

public interface ContractService {
    ContractFormDto getContractForm(String token, Long applicationId);

    String generateContract(ContractFormDto dto);

    Long missContract(ContractFormDto dto);

    Integer getCommission(Integer sum, Long objectTypeId);
}
