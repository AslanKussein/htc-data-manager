package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.service.PropertyOwnerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-owners")
public class PropertyOwnerResource {
    private final PropertyOwnerManager propertyOwnerManager;

    @Autowired
    public PropertyOwnerResource(PropertyOwnerManager propertyOwnerManager) {
        this.propertyOwnerManager = propertyOwnerManager;
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
