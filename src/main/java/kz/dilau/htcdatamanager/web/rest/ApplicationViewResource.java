package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationViewService;
import kz.dilau.htcdatamanager.web.dto.ApplicationViewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATION_VIEW_REST_ENDPOINT)
public class ApplicationViewResource {
    private final ApplicationViewService applicationViewService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationViewDTO> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                      @PathVariable("id") Long id) {
         ApplicationViewDTO result = applicationViewService.getById(token, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/compare")
    public ResponseEntity<List<ApplicationViewDTO>> getApplicationsForCompare(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                                              @RequestParam(value = "ids[]") List<Long> ids) {
        List<ApplicationViewDTO> result = applicationViewService.getApplicationsForCompare(token, ids);
        return ResponseEntity.ok(result);
    }

}
