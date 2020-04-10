package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.web.rest.vm.dictionary.DictionaryDto;

import java.util.List;

public interface DictionaryManager {
    DictionaryDto getDictionaryById(Long id);

    List<DictionaryDto> getAllDictionaries();

    Long saveDictionary(DictionaryDto dictionary);

    void updateDictionary(Long id, DictionaryDto dictionary);

    void deleteDictionaryById(Long id);
}
