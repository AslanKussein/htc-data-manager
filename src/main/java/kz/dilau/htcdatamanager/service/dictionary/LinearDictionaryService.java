package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;

public interface LinearDictionaryService {
    Long save(DictionaryItemRequestDto dto);
    Long update(Long id, DictionaryItemRequestDto dto);
    Long delete(Long id, DictionaryItemRequestDto dto);
}
