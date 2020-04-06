package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/cities")
public class CityResource {
    private final CityRepository cityRepository;

    @GetMapping("")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityRepository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveCity(@RequestBody City city) {
        return ResponseEntity.ok(cityRepository.save(city).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCityById(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateCity(@RequestBody City city) {
        cityRepository.save(city);
        return ResponseEntity.noContent().build();
    }
}
