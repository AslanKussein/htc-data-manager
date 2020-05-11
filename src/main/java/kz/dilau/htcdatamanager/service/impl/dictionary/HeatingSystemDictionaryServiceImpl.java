package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.HeatingSystem;
import kz.dilau.htcdatamanager.repository.dictionary.HeatingSystemRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("HeatingSystem")
public class HeatingSystemDictionaryServiceImpl implements LinearDictionaryService {
    private final HeatingSystemRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new HeatingSystem(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        HeatingSystem byId = cacheService.getById(HeatingSystem.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        HeatingSystem byId = cacheService.getById(HeatingSystem.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return null;
    }

    private HeatingSystem saveDict(HeatingSystem dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
