package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;

import java.util.List;

public interface LinearDictionaryService {
    Long save(DictionaryItemRequestDto dto);

    Long update(Long id, DictionaryItemRequestDto dto);

    Long delete(Long id);

    List childList(Long parentId);
}
