package kz.dilau.htcdatamanager.web.rest.client;

import io.swagger.annotations.ApiParam;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_CLIENT_AUTO_CREATE_REST_ENDPOINT)
public class ApplicationClientAutoCreateResource {
    private final ApplicationService applicationService;



    @PostMapping
    public ResponseEntity<Long> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                     @RequestBody ApplicationDto dto) {
        Long result = applicationService.save(token, dto);
        return ResponseEntity.ok(result);
    }


}
