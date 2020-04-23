package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.service.CommonResource;
import kz.dilau.htcdatamanager.service.RealPropertyOwnerService;
import kz.dilau.htcdatamanager.web.dto.RealPropertyOwnerDto;
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
@RequestMapping(Constants.OWNERS_REST_ENDPOINT)
public class RealPropertyOwnerResource implements CommonResource<Long, RealPropertyOwnerDto, RealPropertyOwnerDto> {
    private final RealPropertyOwnerService realPropertyOwnerService;

    @Override
    public ResponseEntity<RealPropertyOwnerDto> getById(String token, Long id) {
        RealPropertyOwnerDto owner = realPropertyOwnerService.getById(token, id);
        return ResponseEntity.ok(owner);
    }

    @ApiIgnore
    @Override
    public ResponseEntity<List<RealPropertyOwnerDto>> getAll(String token) {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                String.format("Get all owners not implemented")
        );
    }

    @Override
    public ResponseEntity<Long> save(String token, RealPropertyOwnerDto input) {
        Long id = realPropertyOwnerService.save(token, input);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<?> update(String token, Long id, RealPropertyOwnerDto dto) {
        realPropertyOwnerService.update(token, id, dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long id) {
        realPropertyOwnerService.deleteById(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-phone-number")
    public ResponseEntity<RealPropertyOwnerDto> findOwnerByPhoneNumber(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                                       @RequestParam String phoneNumber) {
        RealPropertyOwnerDto owner = realPropertyOwnerService.findOwnerByPhoneNumber(phoneNumber);
        return nonNull(owner) ? ResponseEntity.ok(owner) : ResponseEntity.noContent().build();
    }
}
