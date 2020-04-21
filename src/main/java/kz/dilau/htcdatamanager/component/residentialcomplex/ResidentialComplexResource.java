package kz.dilau.htcdatamanager.component.residentialcomplex;

import kz.dilau.htcdatamanager.component.common.CommonResource;
import kz.dilau.htcdatamanager.component.common.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/residential-complexes")
public class ResidentialComplexResource implements CommonResource<Long, ResidentialComplexDto, ResidentialComplexDto> {
    private final ResidentialComplexManager residentialComplexManager;

    @Override
    public ResponseEntity<ResidentialComplexDto> getById(String token, Long id) {
        try {
            ResidentialComplexDto dto = residentialComplexManager.getById(token, id);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Residential complex with id %s not found", id, e)
            );
        }
    }

    @Override
    public ResponseEntity<List<ResidentialComplexDto>> getAll(String token) {
        List<ResidentialComplexDto> list = residentialComplexManager.getAll(token);
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<?> deleteById(String token, Long id) {
        try {
            residentialComplexManager.deleteById(token, id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Residential complex with id %s not found", id, e)
            );
        }
    }

    @Override
    public ResponseEntity<?> update(String token, Long id, ResidentialComplexDto dto) {
        try {
            residentialComplexManager.update(token, id, dto);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Provide correct residential complex id: %s", id)
            );
        }
    }

    @Override
    public ResponseEntity<Long> save(String token, ResidentialComplexDto input) {
        Long id = residentialComplexManager.save(token, input);
        return ResponseEntity.ok(id);
    }
}
