package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.service.dictionary.DictionaryManager;
import kz.dilau.htcdatamanager.web.rest.vm.dictionary.DictionaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractDictionaryResource<S extends DictionaryManager> implements CommonDictionaryResource {
    private final S service;

    @Override
    public ResponseEntity<DictionaryDto> getDictionaryById(Long id) {
        return ResponseEntity.ok(service.getDictionaryById(id));
    }

    @Override
    public ResponseEntity<List<DictionaryDto>> getAllDictionaries() {
        return ResponseEntity.ok(service.getAllDictionaries());
    }

    @Override
    public ResponseEntity<Long> saveDictionary(DictionaryDto dictionary) {
        return ResponseEntity.ok(service.saveDictionary(dictionary));
    }

    @Override
    public ResponseEntity<Void> updateDictionary(Long id, DictionaryDto dictionary) {
        service.updateDictionary(id, dictionary);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteDictionaryById(Long id) {
        service.deleteDictionaryById(id);
        return ResponseEntity.noContent().build();
    }
}
