package kz.dilau.htcdatamanager.module;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public interface CommonResource<ID, IN, OUT> {
    @GetMapping("/{id}")
    ResponseEntity<OUT> getById(@RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id);

    @GetMapping
    ResponseEntity<List<OUT>> getAll(@RequestHeader(AUTHORIZATION) String token);

    @PostMapping
    ResponseEntity<ID> save(@RequestHeader(AUTHORIZATION) String token, @RequestBody IN input);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id, @RequestBody IN input);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id);
}
