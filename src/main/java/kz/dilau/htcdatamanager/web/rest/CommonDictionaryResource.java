package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.web.rest.vm.dictionary.DictionaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CommonDictionaryResource {
    @GetMapping("/{id}")
    ResponseEntity<DictionaryDto> getDictionaryById(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<DictionaryDto>> getAllDictionaries();

    @PostMapping
    ResponseEntity<Long> saveDictionary(@RequestBody DictionaryDto dictionary);

    @PutMapping("/{id}")
    ResponseEntity<Void> updateDictionary(@PathVariable("id") Long id, @RequestBody DictionaryDto dictionary);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteDictionaryById(@PathVariable("id") Long id);
}
