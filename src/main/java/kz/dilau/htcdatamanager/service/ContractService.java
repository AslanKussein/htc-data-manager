package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;

public interface ContractService {
    ContractFormDto getContractForm(String token, Long applicationId);

    String generateContract(String token, ContractFormDto dto);

    String generateDepositContract(String token, DepositFormDto dto);

    ClientAppContractResponseDto generateClientAppContract(String token, ClientAppContractRequestDto clientAppContractRequestDto);

    Long missContract(ContractFormDto dto);

    Integer getCommission(Integer sum, Long objectTypeId);

    ListResponse<CommissionRangeDto> getAllCommissions();
}
