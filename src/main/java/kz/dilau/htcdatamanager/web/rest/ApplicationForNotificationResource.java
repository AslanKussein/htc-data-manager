package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationClientViewService;
import kz.dilau.htcdatamanager.service.ApplicationForNotificationService;
import kz.dilau.htcdatamanager.web.dto.ApplicationForNotificationDto;
import kz.dilau.htcdatamanager.web.dto.ContractFormForNotificationDto;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATION_FOR_NOTIFICATION_REST_ENDPOINT)
public class ApplicationForNotificationResource {
    private final ApplicationForNotificationService applicationForNotificationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationForNotificationDto> getById(@PathVariable("id") Long id) {
        ApplicationForNotificationDto result = applicationForNotificationService.getById(id);
        return ResponseEntity.ok(result);
    }


}
