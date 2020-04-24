package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.service.CommonResource;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.CLIENTS_REST_ENDPOINT)
public class ClientResource implements CommonResource<Long, ClientDto, ClientDto> {
    private final ClientService clientService;

    @Override
    public ResponseEntity<ClientDto> getById(String token, Long id) {
        ClientDto clientDto = clientService.getById(token, id);
        return ResponseEntity.ok(clientDto);
    }

    @ApiIgnore
    @Override
    public ResponseEntity<List<ClientDto>> getAll(String token) {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                String.format("Get all owners not implemented")
        );
    }

    @Override
    public ResponseEntity<Long> save(String token, ClientDto input) {
        Long id = clientService.save(token, input);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<?> update(String token, Long id, ClientDto dto) {
        clientService.update(token, id, dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long id) {
        clientService.deleteById(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-phone-number")
    public ResponseEntity<ClientDto> findClientByPhoneNumber(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                             @RequestParam String phoneNumber) {
        ClientDto client = clientService.findClientByPhoneNumber(phoneNumber);
        return nonNull(client) ? ResponseEntity.ok(client) : ResponseEntity.noContent().build();
    }
}
