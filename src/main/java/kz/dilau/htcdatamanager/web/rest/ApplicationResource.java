package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.Api;
import kz.dilau.htcdatamanager.service.ApplicationManager;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Api(value = "/applications", description = "Application resource")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/applications")
public class ApplicationResource {
    private final ApplicationManager applicationManager;

    @PostMapping("")
    public ResponseEntity<Long> saveApplication(@RequestHeader(AUTHORIZATION) final String token,
                                                @RequestParam(name = "type", defaultValue = "shortForm") ApplicationType applicationType,
                                                @RequestBody ApplicationDto dto) {
        Long id = applicationManager.saveApplication(token, applicationType, dto);
        return new ResponseEntity<>(id, HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
                                 @RequestBody ApplicationDto application) {
        applicationManager.update(id, application);
        return ResponseEntity.noContent().build();
    }
}
