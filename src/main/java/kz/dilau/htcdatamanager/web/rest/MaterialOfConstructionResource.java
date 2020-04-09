package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.repository.MaterialOfConstructionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/material-of-constructions")
public class MaterialOfConstructionResource {
    private final MaterialOfConstructionRepository repository;
    
    @GetMapping("")
    public ResponseEntity<List<MaterialOfConstruction>> getAllDistricts() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialOfConstruction> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveDistrict(@RequestBody MaterialOfConstruction district) {
        return ResponseEntity.ok(repository.save(district).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDistrictById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateDistrict(@RequestBody MaterialOfConstruction district) {
        repository.save(district);
        return ResponseEntity.noContent().build();
    }
}
