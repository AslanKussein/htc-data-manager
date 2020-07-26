package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.HouseClass;
import kz.dilau.htcdatamanager.repository.dictionary.HouseClassRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("HouseClass")
public class HouseClassDictionaryServiceImpl implements LinearDictionaryService {
    private final HouseClassRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new HouseClass(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        HouseClass byId = cacheService.getById(HouseClass.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        HouseClass byId = cacheService.getById(HouseClass.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return null;
    }

    private HouseClass saveDict(HouseClass dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
