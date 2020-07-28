package kz.dilau.htcdatamanager.web.rest.client;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.service.ApplicationClientService;
import kz.dilau.htcdatamanager.web.dto.client.ClientApplicationCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kz.dilau.htcdatamanager.config.Constants.APPLICATIONS_CLIENT_REST_ENDPOINT;
import static kz.dilau.htcdatamanager.config.Constants.OPEN_API_REST_ENDPOINT;

@ApiModel(description = "Открытый Rest Controller для создания заявки в КП")
@RequiredArgsConstructor
@RestController
@RequestMapping(OPEN_API_REST_ENDPOINT + APPLICATIONS_CLIENT_REST_ENDPOINT)
public class ApplicationClientOpenResource {
    private final ApplicationClientService applicationClientService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ClientApplicationCreateDTO dto) {
        Long result = applicationClientService.save(dto);
        return ResponseEntity.ok(result);
    }
}
