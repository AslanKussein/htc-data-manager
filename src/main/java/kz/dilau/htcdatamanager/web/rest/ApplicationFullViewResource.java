package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationFullViewService;
import kz.dilau.htcdatamanager.web.dto.ApplicationFullViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATION_FULL_VIEW_REST_ENDPOINT)
public class ApplicationFullViewResource {
    private final ApplicationFullViewService applicationFullViewService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationFullViewDto> getById(@PathVariable("id") Long id) {
        ApplicationFullViewDto result = applicationFullViewService.getById(id);
        return ResponseEntity.ok(result);
    }


}
