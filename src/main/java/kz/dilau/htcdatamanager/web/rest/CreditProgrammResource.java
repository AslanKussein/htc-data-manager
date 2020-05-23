package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.CreditProgrammService;
import kz.dilau.htcdatamanager.web.dto.CreditProgrammDto;
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
@RequestMapping(Constants.CREDIT_PROGRAMM_REST_ENDPOINT)
public class CreditProgrammResource {
    private final CreditProgrammService creditProgrammService;

    @GetMapping("/{id}")
    public ResponseEntity<CreditProgrammDto> getById(@PathVariable("id") Long id) {
        CreditProgrammDto dto = creditProgrammService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CreditProgrammDto>> getAll() {
        List<CreditProgrammDto> list = creditProgrammService.getAll();
        return ResponseEntity.ok(list);
    }


    @PostMapping("/getAllPageable")
    public ResponseEntity<PageDto<CreditProgrammDto>> getAllPageable(PageableDto dto) {
        PageDto<CreditProgrammDto> page = creditProgrammService.getAllPageable(dto);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CreditProgrammDto> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                  @RequestBody CreditProgrammDto input) {
        CreditProgrammDto result = creditProgrammService.save(token, input);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CreditProgrammDto> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                        @PathVariable("id") Long id) {
        CreditProgrammDto result = creditProgrammService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditProgrammDto> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                    @PathVariable("id") Long id,
                                                    @RequestBody CreditProgrammDto dto) {
        CreditProgrammDto result = creditProgrammService.update(token, id, dto);
        return ResponseEntity.ok(result);
    }

}

