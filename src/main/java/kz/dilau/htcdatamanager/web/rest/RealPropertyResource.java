package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.service.RealPropertyManager;
import kz.dilau.htcdatamanager.domain.RealProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/real-properties")
public class RealPropertyResource {
    private final RealPropertyManager realPropertyManager;

    @ApiIgnore
    @GetMapping
    public List<RealProperty> getAll() {
        return realPropertyManager.getAll();
    }

    @ApiIgnore
    @GetMapping("/{id}")
    public RealProperty getById(@PathVariable Long id) {
        return realPropertyManager.getById(id);
    }

    @ApiIgnore
    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        realPropertyManager.deleteById(id);
    }

    @ApiIgnore
    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody RealProperty realProperty) {
        realPropertyManager.update(id, realProperty);
    }

    @ApiIgnore
    @PostMapping
    public void save(@RequestBody RealProperty realProperty) {
        realPropertyManager.save(realProperty);
    }

    @PostMapping("/files")
    public ResponseEntity<Void> addFilesToProperty(@RequestParam("propertyId") Long propertyId,
                                                   @RequestParam(value = "photo", required = false) List<String> photoIds,
                                                   @RequestParam(value = "housingPlan", required = false) List<String> housingPlans,
                                                   @RequestParam(value = "propertyId", required = false) List<String> virtualTours) {
        realPropertyManager.addFilesToProperty(propertyId, photoIds, housingPlans, virtualTours);
        return ResponseEntity.noContent().build();
    }
}
