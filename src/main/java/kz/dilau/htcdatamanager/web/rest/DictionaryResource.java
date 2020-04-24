package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.service.dictionary.Dictionary;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/{name}")
public class DictionaryResource {
    private final DictionaryService dictionaryService;

    @GetMapping("/{id}")
    public ResponseEntity<DictionaryDto<Long>> getById(@PathVariable("name") Dictionary dictionaryName,
                                                       @PathVariable("id") Long id) {
        try {
            DictionaryDto<Long> dictionary = dictionaryService.getById(dictionaryName, id);
            return ResponseEntity.ok(dictionary);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Dictionary with id %s not found", id, e)
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<DictionaryDto<Long>>> getAll(@PathVariable("name") Dictionary dictionary) {
        List<DictionaryDto<Long>> dictionaries = dictionaryService.getAll(dictionary);
        return ResponseEntity.ok(dictionaries);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestHeader(AUTHORIZATION) String token,
                                     @PathVariable("name") Dictionary dictionary,
                                     @RequestBody DictionaryDto<Long> input) {
        Long id = dictionaryService.save(token, dictionary, input);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader(AUTHORIZATION) String token,
                                    @PathVariable("name") Dictionary dictionary,
                                    @PathVariable("id") Long id,
                                    @RequestBody DictionaryDto<Long> input) {
        try {
            dictionaryService.update(token, dictionary, id, input);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Provide correct owner id: %s", id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@RequestHeader(AUTHORIZATION) String token,
                                        @PathVariable("name") Dictionary dictionary,
                                        @PathVariable("id") Long id) {
        try {
            dictionaryService.deleteById(token, dictionary, id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Dictionary with id %s not found", id, e)
            );
        }
    }
}
