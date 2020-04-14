package kz.dilau.htcdatamanager.component.dictionary;

import java.util.List;

public interface DictionaryManager {
    DictionaryDto<Long> getById(String token, Dictionary dictionary, Long id);

    List<DictionaryDto<Long>> getAll(String token, Dictionary dictionary);

    Long save(String token, Dictionary dictionary, DictionaryDto<Long> input);

    void update(String token, Dictionary dictionary, Long id, DictionaryDto<Long> input);

    void deleteById(String token, Dictionary dictionary, Long id);
}
