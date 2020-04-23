package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.service.RealPropertyService;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.web.dto.RealPropertyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/real-properties")
public class RealPropertyResource {
    private final RealPropertyService realPropertyService;

    @ApiIgnore
    @GetMapping
    public List<RealPropertyRequestDto> getAll() {
        return realPropertyService.getAll();
    }

    @ApiIgnore
    @GetMapping("/{id}")
    public RealPropertyRequestDto getById(@PathVariable Long id) {
        return realPropertyService.getById(id);
    }

    @ApiIgnore
    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        realPropertyService.deleteById(id);
    }

    @ApiIgnore
    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody RealProperty realProperty) {
        realPropertyService.update(id, realProperty);
    }

    @ApiIgnore
    @PostMapping
    public void save(@RequestBody RealProperty realProperty) {
        realPropertyService.save(realProperty);
    }

    @ApiIgnore
    @PostMapping("/files")
    public ResponseEntity<Void> addFilesToProperty(@RequestParam("propertyId") Long propertyId,
                                                   @RequestParam(value = "photo", required = false) List<String> photoIds,
                                                   @RequestParam(value = "housingPlan", required = false) List<String> housingPlans,
                                                   @RequestParam(value = "propertyId", required = false) List<String> virtualTours) {
        realPropertyService.addFilesToProperty(propertyId, photoIds, housingPlans, virtualTours);
        return ResponseEntity.noContent().build();
    }
}
