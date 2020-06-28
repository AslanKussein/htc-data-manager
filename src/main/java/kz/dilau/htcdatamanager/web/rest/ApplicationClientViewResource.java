package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationClientViewService;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATION_CLIENT_VIEW_REST_ENDPOINT)
public class ApplicationClientViewResource {
    private final ApplicationClientViewService applicationClientViewService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationClientViewDto> getById(@PathVariable("id") Long id) {
        ApplicationClientViewDto result = applicationClientViewService.getById(id);
        return ResponseEntity.ok(result);
    }


}
