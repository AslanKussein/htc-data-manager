package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.repository.dictionary.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/streets")
public class StreetResource {
    private final StreetRepository repository;

    @GetMapping("")
    public ResponseEntity<List<Street>> getAllStreets() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Street> getStreetById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveStreet(@RequestBody Street parkingType) {
        return ResponseEntity.ok(repository.save(parkingType).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStreetById(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateStreet(@RequestBody Street parkingType) {
        repository.save(parkingType);
        return ResponseEntity.noContent().build();
    }
}
