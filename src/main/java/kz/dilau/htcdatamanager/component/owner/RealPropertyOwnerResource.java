package kz.dilau.htcdatamanager.component.owner;

import io.swagger.annotations.Api;
import kz.dilau.htcdatamanager.module.CommonResource;
import kz.dilau.htcdatamanager.web.rest.errors.RealPropertyOwnerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Api
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
    public ResponseEntity<RealPropertyOwnerDto> findOwnerByPhoneNumber(@RequestParam String phoneNumber) {
        RealPropertyOwnerDto owner = rpoManager.findOwnerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(owner);
    }
}
