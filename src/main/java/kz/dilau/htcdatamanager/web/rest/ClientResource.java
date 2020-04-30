package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.CLIENTS_REST_ENDPOINT)
public class ClientResource {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                             @PathVariable("id") Long id) {
        ClientDto clientDto = clientService.getById(token, id);
        return ResponseEntity.ok(clientDto);
    }

    @PostMapping
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto input) {
        ClientDto result = clientService.save(input);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                            @PathVariable("id") Long id,
                                            @RequestBody ClientDto dto) {
        ClientDto result = clientService.update(token, id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                @PathVariable("id") Long id) {
        ClientDto result = clientService.deleteById(token, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/by-phone-number")
    public ResponseEntity<ClientDto> findClientByPhoneNumber(@RequestParam String phoneNumber) {
        ClientDto client = clientService.findClientByPhoneNumber(phoneNumber);
        return nonNull(client) ? ResponseEntity.ok(client) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
