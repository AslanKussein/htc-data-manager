package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.service.PropertyOwnerManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/property-owners")
public class PropertyOwnerResource {
    private final PropertyOwnerManager propertyOwnerManager;

    //todo instead use Rest Repositories
    @GetMapping("/search/find-by-phone-number")
    public ResponseEntity<RealPropertyOwner> searchClientByPhoneNumber(@RequestParam String phoneNumber) {
        RealPropertyOwner owner = propertyOwnerManager.searchClientByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(owner);
    }

    @GetMapping
    public List<RealPropertyOwner> getAll() {
        return propertyOwnerManager.getAll();
    }

    @GetMapping("/{id}")
    public RealPropertyOwner getById(@PathVariable Long id) {
        return propertyOwnerManager.getById(id);
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        propertyOwnerManager.deleteById(id);
    }

    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody RealPropertyOwner propertyOwner) {
        propertyOwnerManager.update(id, propertyOwner);
    }

    @PostMapping
    public void save(@RequestBody RealPropertyOwner realProperty) {
        propertyOwnerManager.save(realProperty);
    }
}
