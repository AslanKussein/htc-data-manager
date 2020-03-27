package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.Api;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.service.ApplicationManager;
import kz.dilau.htcdatamanager.web.rest.vm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Api(value = "/applications", description = "Application operations")
@RestController
@RequestMapping("/applications")
public class ApplicationResource {
    private final ApplicationManager applicationManager;

    @Autowired
    public ApplicationResource(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @PostMapping("")
    public ResponseEntity<Long> saveApplication(@RequestHeader(AUTHORIZATION) final String token,
                                                @RequestParam(defaultValue = "short") ApplicationType applicationType,
                                                @RequestBody ApplicationDto dto) {
        Long id = applicationManager.saveApplication(token, applicationType, dto);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/recently-created")
    public ResponseEntity<List<RecentlyCreatedApplication>> getRecentlyCreatedApps(@RequestHeader(AUTHORIZATION) final String token) {
        List<RecentlyCreatedApplication> list = applicationManager.getRecentlyCreatedApps(token);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@RequestHeader(AUTHORIZATION) final String token,
                                                  @PathVariable Long id) {
        ApplicationDto applicationDto = applicationManager.getById(token, id);
        return ResponseEntity.ok(applicationDto);
    }


    @PostMapping("/short-form")
    public ResponseEntity<Long> saveShortFormApplication(@RequestBody ShortFormApplication shortFormApplication) {
        Long id = applicationManager.saveShortFormApplication(shortFormApplication);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PostMapping("/full-form/sell")
    public ResponseEntity<Long> saveApplicationForSell(@RequestBody ApplicationForSell applicationForSell) {
//        Long id = applicationManager.saveApplicationForSell(applicationForSell);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/full-form/buy")
    public ResponseEntity<Long> saveApplicationForBuy(@RequestBody ApplicationForBuy applicationForBuy) {
//        Long id = applicationManager.saveApplicationForBuy(applicationForBuy);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        List<Application> applications = applicationManager.getAll();
        return ResponseEntity.ok(applications);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Application> getById(@PathVariable Long id) {
//        Application application = applicationManager.getById(id);
//        return ResponseEntity.ok(application);
//    }

    @PostMapping("/{id}/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        applicationManager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestBody Application application) {
        applicationManager.update(id, application);
        return ResponseEntity.noContent().build();
    }
}
