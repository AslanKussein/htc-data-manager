package kz.dilau.htcdatamanager.service.dictionary;

import java.util.List;

public interface DictionaryService {
    DictionaryDto<Long> getById(Dictionary dictionary, Long id);

    List<DictionaryDto<Long>> getAll(Dictionary dictionary);

    Long save(String token, Dictionary dictionary, DictionaryDto<Long> input);

    void update(String token, Dictionary dictionary, Long id, DictionaryDto<Long> input);

    void deleteById(String token, Dictionary dictionary, Long id);
}
