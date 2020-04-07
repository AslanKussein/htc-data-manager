package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.repository.dictionary.ParkingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/parking-types")
public class ParkingTypeResource {
    private final ParkingTypeRepository parkingTypeRepository;

    @GetMapping("")
    public ResponseEntity<List<ParkingType>> getAllParkingTypes() {
        return ResponseEntity.ok(parkingTypeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingType> getParkingTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingTypeRepository.getOne(id));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<Long> saveOperationType(@RequestBody ParkingType parkingType) {
        return ResponseEntity.ok(parkingTypeRepository.save(parkingType).getId());
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperationTypeById(@PathVariable Long id) {
        parkingTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("")
    public ResponseEntity updateOperationType(@RequestBody ParkingType parkingType) {
        parkingTypeRepository.save(parkingType);
        return ResponseEntity.noContent().build();
    }
}
