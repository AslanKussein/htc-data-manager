package kz.dilau.htcdatamanager.web.rest.client;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ApplicationClientService;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ApiModel(description = "Rest Controller для создания заявки в КП")
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APPLICATIONS_CLIENT_REST_ENDPOINT)
public class ApplicationClientResource {
    private final ApplicationClientService applicationClientService;


    @GetMapping("/{id}")
    public ResponseEntity<ApplicationClientDTO> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                        @PathVariable("id") Long id) {
        ApplicationClientDTO result = applicationClientService.getById(token, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllMyAppByOperationTypeId/{operationTypeId}")
    public ResponseEntity<List<ApplicationClientDTO>> getAllMyAppByOperationTypeId(@ApiIgnore @AuthenticationPrincipal final Principal principal,
                                                                                   @ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                                                   @PathVariable("operationTypeId") Long operationTypeId) {

        return ResponseEntity.ok(applicationClientService.getAllMyAppByOperationTypeId(principal.getName(), token, operationTypeId));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ApplicationClientDTO dto,
                                       @ApiIgnore @RequestHeader(AUTHORIZATION) String token) {
        Long result = applicationClientService.save(token, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                       @PathVariable("id") Long id,
                                       @RequestBody ApplicationClientDTO dto) {
        Long result = applicationClientService.update(token, id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                           @PathVariable("id") Long id) {
        Long result = applicationClientService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }
}
