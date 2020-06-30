package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.CommissionRangeDto;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
import kz.dilau.htcdatamanager.web.dto.DepositFormDto;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

public interface ContractService {
    ContractFormDto getContractForm(String token, Long applicationId);

    String generateContract(ContractFormDto dto);

    String generateDepositContract(DepositFormDto dto);

    Long missContract(ContractFormDto dto);

    Integer getCommission(Integer sum, Long objectTypeId);

    ListResponse<CommissionRangeDto> getAllCommissions();
}
