package kz.dilau.htcdatamanager.component.owner;

import kz.dilau.htcdatamanager.component.common.CommonResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping("/property-owners")
public class RealPropertyOwnerResource implements CommonResource<Long, RealPropertyOwnerDto, RealPropertyOwnerDto> {
    private final RealPropertyOwnerManager rpoManager;

    @Override
    public ResponseEntity<RealPropertyOwnerDto> getById(String token, Long id) {
        try {
            RealPropertyOwnerDto owner = rpoManager.getById(token, id);
            return ResponseEntity.ok(owner);
        } catch (RealPropertyOwnerNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Owner with id %s not found", id, e)
            );
        }
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
        Long id = rpoManager.save(token, input);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<?> update(String token, Long id, RealPropertyOwnerDto dto) {
        try {
            rpoManager.update(token, id, dto);
            return ResponseEntity.noContent().build();
        } catch (RealPropertyOwnerNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Provide correct owner id: %s", id)
            );
        }
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long id) {
        try {
            rpoManager.deleteById(token, id);
            return ResponseEntity.noContent().build();
        } catch (RealPropertyOwnerNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Owner with id %s not found", id)
            );
        }
    }

    @GetMapping("/search/by-phone-number")
    public ResponseEntity<RealPropertyOwnerDto> findOwnerByPhoneNumber(@ApiIgnore @RequestHeader(AUTHORIZATION) String token,
                                                                       @RequestParam String phoneNumber) {
        RealPropertyOwnerDto owner = rpoManager.findOwnerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(owner);
    }
}
