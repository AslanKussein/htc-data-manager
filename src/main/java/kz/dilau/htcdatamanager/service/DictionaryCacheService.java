package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryFilterDto;

import java.util.List;

public interface DictionaryCacheService {
    PageDto<BaseCustomDictionary> getDictionary(DictionaryFilterDto filterDto);

    List<BaseCustomDictionary> getDictionary(String dictionaryName);

    void clearDictionaries();

    boolean isEditable(String dictionaryName);

    <T extends BaseCustomDictionary> T getById(Class<T> clazz, Long id);

    BaseCustomDictionary loadDictionaryByIdFromDatabase(String dictionaryEntityName, Long id);
}
