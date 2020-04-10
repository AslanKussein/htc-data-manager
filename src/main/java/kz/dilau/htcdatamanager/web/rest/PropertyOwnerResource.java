package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.Api;
import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.service.PropertyOwnerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Example Controller", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@RestController
@RequestMapping("/property-owners")
public class PropertyOwnerResource {
    private final PropertyOwnerManager propertyOwnerManager;

    @GetMapping("/{id}")
    public ResponseEntity<RealPropertyOwner> getOwnerById(@PathVariable Long id) {
        RealPropertyOwner owner = propertyOwnerManager.getOwnerById(id);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/search/by-phone-number")
    public ResponseEntity<RealPropertyOwner> findOwnerByPhoneNumber(@RequestParam String phoneNumber) {
        RealPropertyOwner owner = propertyOwnerManager.findOwnerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(owner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwnerById(@PathVariable Long id) {
        propertyOwnerManager.deleteOwnerById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long id, @RequestBody RealPropertyOwner propertyOwner) {
        propertyOwnerManager.updateOwner(id, propertyOwner);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Long> saveOwner(@RequestBody RealPropertyOwner realProperty) {
        Long id = propertyOwnerManager.saveOwner(realProperty);
        return ResponseEntity.ok(id);
    }
}
