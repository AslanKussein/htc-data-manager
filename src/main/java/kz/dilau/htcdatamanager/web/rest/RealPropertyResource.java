package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.service.RealPropertyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/real-properties")
public class RealPropertyResource {
    private final RealPropertyManager realPropertyManager;

    @GetMapping
    public List<RealProperty> getAll() {
        return realPropertyManager.getAll();
    }

    @GetMapping("/{id}")
    public RealProperty getById(@PathVariable Long id) {
        return realPropertyManager.getById(id);
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        realPropertyManager.deleteById(id);
    }

    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody RealProperty realProperty) {
        realPropertyManager.update(id, realProperty);
    }

    @PostMapping
    public void save(@RequestBody RealProperty realProperty) {
        realPropertyManager.save(realProperty);
    }
}
