package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.CONTRACTS_REST_ENDPOINT)
public class ContractResource {
    private final ContractService contractService;

    @GetMapping("/{applicationId}")
    public ResponseEntity<ContractFormDto> getContractForm(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                           @PathVariable("applicationId") Long applicationId) {
        ContractFormDto result = contractService.getContractForm(token, applicationId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generateContract")
    public ResponseEntity<FileInfoDto> generateContract(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @RequestBody ContractFormDto dto) {
        return ResponseEntity.ok(contractService.generateContract(token, dto));
    }

    @PostMapping("/generateClientAppContract")
    public ResponseEntity<ClientAppContractResponseDto> generateClientAppContract(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @RequestBody ClientAppContractRequestDto clientAppContractRequestDto) {
        ClientAppContractResponseDto result = contractService.generateClientAppContract(token, clientAppContractRequestDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generateDepositContract")
    public ResponseEntity<FileInfoDto> generateDepositContract(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @RequestBody DepositFormDto dto) {
        return ResponseEntity.ok(contractService.generateDepositContract(token, dto));
    }

    @PostMapping("/missContract")
    public ResponseEntity<Long> missContract(@RequestBody ContractFormDto dto) {
        Long result = contractService.missContract(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getCommission")
    public ResponseEntity<Integer> getCommission(@RequestParam("sum") Integer sum,
                                                 @RequestParam("objectTypeId") Long objectTypeId) {
        return ResponseEntity.ok(contractService.getCommission(sum, objectTypeId));
    }

    @GetMapping("/getAllCommissions")
    public ResponseEntity<ListResponse<CommissionRangeDto>> getAllCommissions() {
        return ResponseEntity.ok(contractService.getAllCommissions());
    }
}
