package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.District;
import kz.dilau.htcdatamanager.domain.dictionary.Street;
import kz.dilau.htcdatamanager.repository.dictionary.StreetRepository;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.dictionary.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("Street")
public class StreetDictionaryService implements LinearDictionaryService {
    private final StreetRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new Street(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        Street byId = cacheService.getById(Street.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id, DictionaryItemRequestDto dto) {
        Street byId = cacheService.getById(Street.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private Street saveDict(Street dict, DictionaryItemRequestDto itemDto) {
        dict.setDistrict(cacheService.getById(District.class, itemDto.getParentId()));
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
