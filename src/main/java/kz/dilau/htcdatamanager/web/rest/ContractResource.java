package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.CONTRACTS_REST_ENDPOINT)
public class ContractResource {
    private final ContractService contractService;

    @GetMapping("/{path}")
    public ResponseEntity getById(@PathVariable("path") String path) {
        String result = contractService.generateReport(path);
        return ResponseEntity.ok(result);
    }
}
