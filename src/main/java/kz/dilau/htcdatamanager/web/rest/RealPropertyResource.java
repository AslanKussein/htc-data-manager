package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.service.RealPropertyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/real-properties")
public class RealPropertyResource {
    private final RealPropertyManager realPropertyManager;

    @Autowired
    public RealPropertyResource(RealPropertyManager realPropertyManager) {
        this.realPropertyManager = realPropertyManager;
    }

    @GetMapping
    public List<RealProperty> getAll() {
        return realPropertyManager.getAll();
    }

    @GetMapping("/{id}")
    public RealProperty getById(@PathVariable Long id) {
        return realPropertyManager.getById(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        realPropertyManager.deleteById(id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody RealProperty realProperty) {
        realPropertyManager.update(id, realProperty);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public void save(@RequestBody RealProperty realProperty) {
        realPropertyManager.save(realProperty);
    }
}
