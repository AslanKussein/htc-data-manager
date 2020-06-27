package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.CommissionRange;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
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
    public ResponseEntity<String> generateContract(@RequestBody ContractFormDto dto) {
        String result = contractService.generateContract(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generateContractAvans")
    public ResponseEntity<String> generateContractAvans(@RequestBody ContractFormDto dto) {
        String result = contractService.generateContractAvans(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/generateContractHandsel")
    public ResponseEntity<String> generateContractHandsel(@RequestBody ContractFormDto dto) {
        String result = contractService.generateContractHandsel(dto);
        return ResponseEntity.ok(result);
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
    public ResponseEntity<ListResponse<CommissionRange>> getAllCommissions() {
        return ResponseEntity.ok(contractService.getAllCommissions());
    }
}
