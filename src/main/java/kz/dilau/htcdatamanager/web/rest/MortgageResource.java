package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.MortgageService;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.MORTGAGE_REST_ENDPOINT)
public class MortgageResource {
    private final MortgageService mortgageService;


    @PostMapping
    public ResponseEntity<MortgageDto> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                            @RequestBody MortgageDto input) {
        MortgageDto result = mortgageService.save(token, input);
        return ResponseEntity.ok(result);
    }
}
