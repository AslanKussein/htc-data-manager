package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ResidentialComplexService;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
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
@RequestMapping(Constants.RESIDENTIAL_COMPLEXES_REST_ENDPOINT)
public class ResidentialComplexResource {
    private final ResidentialComplexService residentialComplexService;

    @GetMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> getById(@PathVariable("id") Long id) {
        ResidentialComplexDto dto = residentialComplexService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/byPostcode/{postcode}")
    public ResponseEntity<ResidentialComplexDto> getByPostcode(@PathVariable("postcode") String postcode) {
        ResidentialComplexDto dto = residentialComplexService.getByPostcode(postcode);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/byHouseName/{houseName}")
    public ResponseEntity<PageDto<ResidentialComplexDto>> getByHouseName(@PathVariable("houseName") String houseName) {
        PageDto<ResidentialComplexDto> page = residentialComplexService.getByHouseName(houseName);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<List<ResidentialComplexDto>> getAll() {
        List<ResidentialComplexDto> list = residentialComplexService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/getAllPageable")
    public ResponseEntity<PageDto<ResidentialComplexDto>> getAllPageable(PageableDto dto) {
        PageDto<ResidentialComplexDto> page = residentialComplexService.getAllPageable(dto);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                            @PathVariable("id") Long id) {
        ResidentialComplexDto result = residentialComplexService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                        @PathVariable("id") Long id,
                                                        @RequestBody ResidentialComplexDto dto) {
        ResidentialComplexDto result = residentialComplexService.update(token, id, dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ResidentialComplexDto> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                      @RequestBody ResidentialComplexDto input) {
        ResidentialComplexDto result = residentialComplexService.save(token, input);
        return ResponseEntity.ok(result);
    }
}
