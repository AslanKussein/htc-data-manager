package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.PropertyDeveloper;
import kz.dilau.htcdatamanager.repository.dictionary.PropertyDeveloperRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("PropertyDeveloper")
public class PropertyDeveloperDictionaryServiceImpl implements LinearDictionaryService {
    private final PropertyDeveloperRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new PropertyDeveloper(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        PropertyDeveloper byId = cacheService.getById(PropertyDeveloper.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        PropertyDeveloper byId = cacheService.getById(PropertyDeveloper.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private PropertyDeveloper saveDict(PropertyDeveloper dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
