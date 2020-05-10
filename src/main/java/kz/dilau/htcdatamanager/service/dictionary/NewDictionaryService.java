package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.NonNull;

public interface NewDictionaryService {
    Long save(@NonNull DictionaryItemRequestDto updateDto);

    Long delete(@NonNull Long id, @NonNull DictionaryItemRequestDto updateDto);

    Long update(@NonNull Long id, @NonNull DictionaryItemRequestDto updateDto);
}
