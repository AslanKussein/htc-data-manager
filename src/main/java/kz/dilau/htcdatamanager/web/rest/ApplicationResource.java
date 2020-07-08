package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.ApiParam;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationLightDto;
import kz.dilau.htcdatamanager.web.dto.AssignmentDto;
import kz.dilau.htcdatamanager.web.dto.MetadataWithApplicationsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Long> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                     @RequestBody ApplicationDto dto) {
        Long result = applicationService.save(token, dto);
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

    @GetMapping("/getApartmentByNumberAndPostcode/{apartmentNumber}/{postcode}")
    public ResponseEntity<MetadataWithApplicationsDto> getApartmentByNumberAndPostcode(@PathVariable("apartmentNumber") String apartmentNumber,
                                                                                       @PathVariable("postcode") String postcode) {
        return ResponseEntity.ok(applicationService.getApartmentByNumberAndPostcode(apartmentNumber, postcode));
    }

    @PostMapping("/getNotApprovedMetadata")
    public ResponseEntity<Page<ApplicationDto>> getNotApprovedMetadata(@ApiParam Pageable pageable) {
        return ResponseEntity.ok(applicationService.getNotApprovedMetadata(pageable));
    }

    @PostMapping("/getNotApprovedFiles")
    public ResponseEntity<Page<ApplicationDto>> getNotApprovedFiles(@ApiParam Pageable pageable) {
        return ResponseEntity.ok(applicationService.getNotApprovedFiles(pageable));
    }

    @GetMapping("/approveMetadata/{applicationId}/{statusId}")
    public ResponseEntity<Long> approveMetadata(@PathVariable("applicationId") Long applicationId,
                                                @PathVariable("statusId") Long statusId) {
        return ResponseEntity.ok(applicationService.approveMetadata(applicationId, statusId));
    }

    @GetMapping("/approveFiles/{applicationId}/{statusId}")
    public ResponseEntity<Long> approveFiles(@PathVariable("applicationId") Long applicationId,
                                             @PathVariable("statusId") Long statusId) {
        return ResponseEntity.ok(applicationService.approveFiles(applicationId, statusId));
    }

    @GetMapping("/approveReserve/{applicationId}")
    public ResponseEntity<Long> approveReserve(@PathVariable("applicationId") Long applicationId) {
        return ResponseEntity.ok(applicationService.approveReserve(applicationId));
    }
}
