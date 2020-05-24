package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.CreditProgrammService;
import kz.dilau.htcdatamanager.web.dto.CreditProgrammDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.CREDIT_PROGRAMM_OPEN_REST_ENDPOINT)
public class CreditProgrammOpenResource {

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

}
