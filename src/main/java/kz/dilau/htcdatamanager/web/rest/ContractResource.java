package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ContractService;
import kz.dilau.htcdatamanager.web.dto.ContractFormDto;
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
    public ResponseEntity<ContractFormDto> generateContract(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                            @PathVariable("applicationId") Long applicationId) {
        ContractFormDto result = contractService.getContractForm(token, applicationId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> generateContract(@RequestBody ContractFormDto dto) {
        String result = contractService.generateContract(dto);
        return ResponseEntity.ok(result);
    }
}
