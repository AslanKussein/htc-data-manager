package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.HouseCondition;
import kz.dilau.htcdatamanager.repository.dictionary.HouseConditionRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("HouseCondition")
public class HouseConditionDictionaryServiceImpl implements LinearDictionaryService {
    private final HouseConditionRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new HouseCondition(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        HouseCondition byId = cacheService.getById(HouseCondition.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        HouseCondition byId = cacheService.getById(HouseCondition.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return null;
    }

    private HouseCondition saveDict(HouseCondition dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
