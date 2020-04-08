package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.repository.dictionary.ResidentialComplexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/residentialComplexes")
public class ResidentialComplexResource {
    private final ResidentialComplexRepository repository;

    @GetMapping("")
    public ResponseEntity<List<ResidentialComplex>> getAllResidentialComplexes() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentialComplex> getResidentialComplexById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveResidentialComplex(@RequestBody ResidentialComplex parkingType) {
        return ResponseEntity.ok(repository.save(parkingType).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResidentialComplexById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateResidentialComplex(@RequestBody ResidentialComplex parkingType) {
        repository.save(parkingType);
        return ResponseEntity.noContent().build();
    }
}
