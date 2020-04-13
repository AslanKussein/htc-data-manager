package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.service.dictionary.ResidentialComplexManager;
import kz.dilau.htcdatamanager.web.rest.vm.ResidentialComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/residentialComplexes")
public class ResidentialComplexResource {
    private final ResidentialComplexManager residentialComplexManager;

    @GetMapping("")
    public ResponseEntity<List<ResidentialComplexDto>> getAllResidentialComplexes() {
        return ResponseEntity.ok(residentialComplexManager.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentialComplexDto> getResidentialComplexById(@PathVariable Long id) {
        return ResponseEntity.ok(residentialComplexManager.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveResidentialComplex(@RequestBody ResidentialComplexDto dto) {
        return ResponseEntity.ok(residentialComplexManager.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResidentialComplexById(@PathVariable Long id) {
        residentialComplexManager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateResidentialComplex(@PathVariable Long id,
                                                   @RequestBody ResidentialComplexDto dto) {
        residentialComplexManager.update(id, dto);
        return ResponseEntity.noContent().build();
    }
}
