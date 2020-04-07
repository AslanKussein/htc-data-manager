package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.Country;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.repository.dictionary.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/districts")
public class DistrictResource {
    private final DistrictRepository districtRepository;

    @GetMapping("")
    public ResponseEntity<List<District>> getAllDistricts() {
        return ResponseEntity.ok(districtRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(districtRepository.getOne(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<Long> saveDistrict(@RequestBody District district) {
        return ResponseEntity.ok(districtRepository.save(district).getId());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDistrictById(@PathVariable Long id) {
        districtRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("")
    public ResponseEntity updateDistrict(@RequestBody District district) {
        districtRepository.save(district);
        return ResponseEntity.noContent().build();
    }
}
