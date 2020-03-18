package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.service.ApplicationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationResource {
    private final ApplicationManager applicationManager;

    @Autowired
    public ApplicationResource(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @GetMapping
    public List<Application> getAll() {
        return applicationManager.getAll();
    }

    @GetMapping("/{id}")
    public Application getById(@PathVariable Long id) {
        return applicationManager.getById(id);
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        applicationManager.deleteById(id);
    }

    @PostMapping("/{id}/edit")
    public void update(@PathVariable Long id,
                       @RequestBody Application application) {
        applicationManager.update(id, application);
    }

    @PostMapping
    public void save(@RequestBody Application application) {
        applicationManager.save(application);
    }
}
