package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import kz.dilau.htcdatamanager.repository.dictionary.ObjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dictionaries/objectTypes")
public class ObjectTypeResource {
    private final ObjectTypeRepository objectTypeRepository;

    @GetMapping("")
    public ResponseEntity<List<ObjectType>> getAllObjectTypes() {
        return ResponseEntity.ok(objectTypeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectType> getObjectTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(objectTypeRepository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveObjectType(@RequestBody ObjectType objectType) {
        return ResponseEntity.ok(objectTypeRepository.save(objectType).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteObjectTypeById(@PathVariable Long id) {
        objectTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateObjectType(@RequestBody ObjectType objectType) {
        objectTypeRepository.save(objectType);
        return ResponseEntity.noContent().build();
    }
}
