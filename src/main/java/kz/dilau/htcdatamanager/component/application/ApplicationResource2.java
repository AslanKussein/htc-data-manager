package kz.dilau.htcdatamanager.component.application;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.dilau.htcdatamanager.service.ApplicationManager2;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/applications2")
public class ApplicationResource2 {
    private final ApplicationManager2 applicationManager2;

    @ApiOperation(
            httpMethod = "POST",
            value = "Save application",
            response = Long.class
    )
    @PostMapping("")
    public ResponseEntity<Long> saveApplication(@ApiParam(value = "Bearer token", required = true)
                                                @RequestHeader(AUTHORIZATION) final String token,
                                                @ApiParam(
                                                        value = "Application type",
                                                        required = true,
                                                        type = "string",
                                                        defaultValue = "SHORT_FORM"
                                                )
                                                @RequestParam(name = "type", defaultValue = "SHORT_FORM") ApplicationType applicationType,
                                                @ApiParam(value = "Application data", required = true)
                                                @RequestBody ApplicationDto application) {
        Long id = applicationManager2.saveApplication(token, applicationType, application);
        return ResponseEntity.ok(id);
    }

    @ApiOperation("Get recently created applications(get last 10 applications)")
    @GetMapping("/recently-created")
    public ResponseEntity<List<RecentlyCreatedApplication>> getRecentlyCreatedApplications(@ApiParam(value = "Bearer token", required = true)
                                                                                           @RequestHeader(AUTHORIZATION) final String token) {
        List<RecentlyCreatedApplication> list = applicationManager2.getRecentlyCreatedApplications(token);
        return ResponseEntity.ok(list);
    }

    @ApiOperation("Get application by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@ApiParam(value = "Bearer token", required = true)
                                                             @RequestHeader(AUTHORIZATION) final String token,
                                                             @ApiParam(value = "Application ID", required = true)
                                                             @PathVariable Long id) {
        ApplicationDto applicationDto = applicationManager2.getApplicationById(token, id);
        return ResponseEntity.ok(applicationDto);
    }

    @ApiOperation("Update application")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateApplication(@ApiParam(value = "Bearer token", required = true)
                                                  @RequestHeader(AUTHORIZATION) final String token,
                                                  @ApiParam(value = "Application ID", required = true)
                                                  @PathVariable Long id,
                                                  @ApiParam(value = "Application data", required = true)
                                                  @RequestBody ApplicationDto application) {
        applicationManager2.updateApplication(token, id, application);
        return ResponseEntity.noContent().build();
    }
}
