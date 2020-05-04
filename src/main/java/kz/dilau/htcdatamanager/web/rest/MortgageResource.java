package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.MortgageService;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import kz.dilau.htcdatamanager.web.dto.MortgageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.MORTGAGE_REST_ENDPOINT)
public class MortgageResource {
    private final MortgageService mortgageService;

    @GetMapping("/{id}")
    public ResponseEntity<MortgageDto> getById(@PathVariable("id") Long id) {
        MortgageDto dto = mortgageService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<MortgageDto>> getAll() {
        List<MortgageDto> list = mortgageService.getAll();
        return ResponseEntity.ok(list);
    }


    @PostMapping("/getAllPageable")
    public ResponseEntity<PageDto<MortgageDto>> getAllPageable(PageableDto dto) {
        PageDto<MortgageDto> page = mortgageService.getAllPageable(dto);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<MortgageDto> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                            @RequestBody MortgageDto input) {
        MortgageDto result = mortgageService.save(token, input);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MortgageDto> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                            @PathVariable("id") Long id) {
        MortgageDto result = mortgageService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MortgageDto> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody MortgageDto dto) {
        MortgageDto result = mortgageService.update(token, id, dto);
        return ResponseEntity.ok(result);
    }

}
