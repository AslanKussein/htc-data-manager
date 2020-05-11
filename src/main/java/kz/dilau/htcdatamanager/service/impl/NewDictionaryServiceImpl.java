package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.DictionaryServiceFactory;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.service.NewDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewDictionaryServiceImpl implements NewDictionaryService {
    private final DictionaryCacheService dictionaryCacheService;
    private final DictionaryServiceFactory factory;

    public Long save(@NonNull DictionaryItemRequestDto updateDto) {
        checkEditable(updateDto.getDictionaryName());
        LinearDictionaryService service = factory.getService(updateDto.getDictionaryName());
        Long result = service.save(updateDto);
        dictionaryCacheService.clearDictionaries();
        return result;
    }

    public Long delete(@NonNull Long id, @NonNull String dictionaryName) {
        checkEditable(dictionaryName);
        LinearDictionaryService service = factory.getService(dictionaryName);
        Long result = service.delete(id);
        dictionaryCacheService.clearDictionaries();
        return result;
    }

    public Long update(@NonNull Long id, @NonNull DictionaryItemRequestDto updateDto) {
        checkEditable(updateDto.getDictionaryName());
        LinearDictionaryService service = factory.getService(updateDto.getDictionaryName());
        Long result = service.update(id, updateDto);
        dictionaryCacheService.clearDictionaries();
        return result;
    }

    private void checkEditable(String dictionaryName) {
        if (!dictionaryCacheService.isEditable(dictionaryName)) {
            throw BadRequestException.createEditDictionary(dictionaryName);
        }
    }
}
