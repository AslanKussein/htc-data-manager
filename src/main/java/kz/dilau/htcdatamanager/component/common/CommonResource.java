package kz.dilau.htcdatamanager.component.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public interface CommonResource<ID, IN, OUT> {
    @GetMapping("/{id}")
    ResponseEntity<OUT> getById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id);

    @GetMapping
    ResponseEntity<List<OUT>> getAll(@ApiIgnore @RequestHeader(AUTHORIZATION) String token);

    @PostMapping
    ResponseEntity<ID> save(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @RequestBody IN input);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id, @RequestBody IN input);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@ApiIgnore @RequestHeader(AUTHORIZATION) String token, @PathVariable("id") ID id);
}
