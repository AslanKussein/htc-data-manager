package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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

    @PostMapping("/saveLightApplication")
    public ResponseEntity<Long> saveLightApplication(@RequestBody ApplicationLightDto dto) {
        Long result = applicationService.saveLightApplication(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reassignApplication")
    public ResponseEntity<Long> reassignApplication(@RequestBody AssignmentDto dto) {
        Long result = applicationService.reassignApplication(dto);
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

    @PostMapping("/changeStatus")
    public ResponseEntity<Long> changeStatus(@RequestBody ChangeStatusDto dto) {
        Long result = applicationService.changeStatus(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getApartmentByNumberAndPostcode/{apartmentNumber}/{postcode}")
    public ResponseEntity<List<ApplicationByRealPropertyDto>> getApartmentByNumberAndPostcode(@PathVariable("apartmentNumber") String apartmentNumber,
                                                                                              @PathVariable("postcode") String postcode) {
        return ResponseEntity.ok(applicationService.getApartmentByNumberAndPostcode(apartmentNumber, postcode));
    }
}
