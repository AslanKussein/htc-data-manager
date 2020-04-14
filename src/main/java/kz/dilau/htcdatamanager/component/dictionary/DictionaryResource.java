package kz.dilau.htcdatamanager.component.dictionary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dictionaries/{name}")
public class DictionaryResource {
    private final DictionaryManager dictionaryManager;

    @GetMapping("/{id}")
    public ResponseEntity<DictionaryDto<Long>> getById(@RequestHeader(AUTHORIZATION) String token,
                                                       @PathVariable("name") Dictionary dictionaryName,
                                                       @PathVariable("id") Long id) {
        DictionaryDto<Long> dictionary = dictionaryManager.getById(token, dictionaryName, id);
        return ResponseEntity.ok(dictionary);
    }

    @GetMapping
    public ResponseEntity<List<DictionaryDto<Long>>> getAll(@RequestHeader(AUTHORIZATION) String token,
                                                            @PathVariable("name") Dictionary dictionary) {
        List<DictionaryDto<Long>> dictionaries = dictionaryManager.getAll(token, dictionary);
        return ResponseEntity.ok(dictionaries);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestHeader(AUTHORIZATION) String token,
                                     @PathVariable("name") Dictionary dictionary,
                                     @RequestBody DictionaryDto<Long> input) {
        Long id = dictionaryManager.save(token, dictionary, input);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader(AUTHORIZATION) String token,
                                    @PathVariable("name") Dictionary dictionary,
                                    @PathVariable("id") Long id,
                                    @RequestBody DictionaryDto<Long> input) {
        dictionaryManager.update(token, dictionary, id, input);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@RequestHeader(AUTHORIZATION) String token,
                                        @PathVariable("name") Dictionary dictionary,
                                        @PathVariable("id") Long id) {
        dictionaryManager.deleteById(token, dictionary, id);
        return ResponseEntity.noContent().build();
    }
}
