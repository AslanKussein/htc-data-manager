package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_REST_ENDPOINT)
public class ApplicationResource {
    private final ApplicationService applicationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                  @PathVariable("id") Long id) {
        ApplicationDto result = applicationService.getById(token, id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ApplicationDto dto) {
        Long result = applicationService.save(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                       @PathVariable("id") Long id,
                                       @RequestBody ApplicationDto input) {
        Long result = applicationService.update(token, id, input);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                           @PathVariable("id") Long id) {
        Long result = applicationService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }
}
