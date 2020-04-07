package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.Country;
import kz.dilau.htcdatamanager.repository.dictionary.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("dictionaries/countries")
public class CountryResource {
    private final CountryRepository countryRepository;
    
    @GetMapping("")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryRepository.getOne(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<Long> saveCountry(@RequestBody Country country) {
        return ResponseEntity.ok(countryRepository.save(country).getId());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCountryById(@PathVariable Long id) {
        countryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("")
    public ResponseEntity updateCountry(@RequestBody Country country) {
        countryRepository.save(country);
        return ResponseEntity.noContent().build();
    }
}
