package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.RealPropertyClientService;
import kz.dilau.htcdatamanager.web.dto.ApplicationClientViewDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.REAL_PROPERTY_CLIENT_VIEW_REST_ENDPOINT)
public class RealPropertyClientViewResource {
    private final RealPropertyClientService realPropertyClientService;
    private final ApplicationService applicationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                  @PathVariable("id") Long id) {
        ApplicationDto result = applicationService.getById(token, id);
        return ResponseEntity.ok(result);
    }


}
