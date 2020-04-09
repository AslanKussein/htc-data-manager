package kz.dilau.htcdatamanager.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.dilau.htcdatamanager.service.ApplicationManager;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Api(value = "/applications")
@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationResource {
    private final ApplicationManager applicationManager;

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
        Long id = applicationManager.saveApplication(token, applicationType, application);
        return ResponseEntity.ok(id);
    }

    @ApiOperation("Get recently created applications(get last 10 applications)")
    @GetMapping("/recently-created")
    public ResponseEntity<List<RecentlyCreatedApplication>> getRecentlyCreatedApplications(@ApiParam(value = "Bearer token", required = true)
                                                                                           @RequestHeader(AUTHORIZATION) final String token) {
        List<RecentlyCreatedApplication> list = applicationManager.getRecentlyCreatedApplications(token);
        return ResponseEntity.ok(list);
    }

    @ApiOperation("Get application by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@ApiParam(value = "Bearer token", required = true)
                                                             @RequestHeader(AUTHORIZATION) final String token,
                                                             @ApiParam(value = "Application ID", required = true)
                                                             @PathVariable Long id) {
        ApplicationDto applicationDto = applicationManager.getApplicationById(token, id);
        return ResponseEntity.ok(applicationDto);
    }

    @ApiOperation("Update application")
    @PutMapping("")
    public ResponseEntity<Void> updateApplication(@ApiParam(value = "Bearer token", required = true)
                                                  @RequestHeader(AUTHORIZATION) final String token,
                                                  @ApiParam(value = "Application data", required = true)
                                                  @RequestBody ApplicationDto application) {
        applicationManager.updateApplication(token, application);
        return ResponseEntity.noContent().build();
    }
}
