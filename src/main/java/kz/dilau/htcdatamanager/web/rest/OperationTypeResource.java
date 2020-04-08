package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.dictionary.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/operationTypes")
public class OperationTypeResource {
    private final OperationTypeRepository operationTypeRepository;

    @GetMapping("")
    public ResponseEntity<List<OperationType>> getAllOperationTypes() {
        return ResponseEntity.ok(operationTypeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationType> getOperationTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(operationTypeRepository.getOne(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> saveOperationType(@RequestBody OperationType operationType) {
        return ResponseEntity.ok(operationTypeRepository.save(operationType).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperationTypeById(@PathVariable Long id) {
        operationTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity updateOperationType(@RequestBody OperationType operationType) {
        operationTypeRepository.save(operationType);
        return ResponseEntity.noContent().build();
    }
}
