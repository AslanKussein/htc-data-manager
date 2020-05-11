package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.Sewerage;
import kz.dilau.htcdatamanager.repository.dictionary.SewerageRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("Sewerage")
public class SewerageDictionaryServiceImpl implements LinearDictionaryService {
    private final SewerageRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new Sewerage(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        Sewerage byId = cacheService.getById(Sewerage.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        Sewerage byId = cacheService.getById(Sewerage.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return null;
    }

    private Sewerage saveDict(Sewerage dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
