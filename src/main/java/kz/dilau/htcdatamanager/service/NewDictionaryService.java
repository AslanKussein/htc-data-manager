package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.NonNull;

import java.util.List;

public interface NewDictionaryService {
    Long save(@NonNull DictionaryItemRequestDto updateDto);

    Long delete(@NonNull Long id, @NonNull String dictionaryName);

    Long update(@NonNull Long id, @NonNull DictionaryItemRequestDto updateDto);

    List getChildList(@NonNull Long parentId, @NonNull String dictionaryName);
}
