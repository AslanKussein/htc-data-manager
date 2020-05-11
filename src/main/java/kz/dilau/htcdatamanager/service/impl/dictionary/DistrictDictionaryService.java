package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.repository.dictionary.DistrictRepository;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.dictionary.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("District")
public class DistrictDictionaryService implements LinearDictionaryService {
    private final DistrictRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new District(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        District byId = cacheService.getById(District.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id, DictionaryItemRequestDto dto) {
        District byId = cacheService.getById(District.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private District saveDict(District dict, DictionaryItemRequestDto itemDto) {
        dict.setCity(cacheService.getById(City.class, itemDto.getParentId()));
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
