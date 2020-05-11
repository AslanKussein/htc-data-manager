package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.dictionary.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("City")
public class CityDictionaryService implements LinearDictionaryService {
    private final CityRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new City(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        City byId = cacheService.getById(City.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id, DictionaryItemRequestDto dto) {
        City byId = cacheService.getById(City.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private City saveDict(City dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
