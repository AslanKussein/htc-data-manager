package kz.dilau.htcdatamanager.web.rest.client;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationViewClientService;
import kz.dilau.htcdatamanager.web.dto.ApplicationViewClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATION_VIEW_CLIENT_REST_ENDPOINT)
public class ApplicationViewClientResource {
    private final ApplicationViewClientService applicationViewClientService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationViewClientDTO> getByIdForClient(@PathVariable("id") Long id) {
        return ResponseEntity.ok(applicationViewClientService.getByIdForClient(id));
    }
}