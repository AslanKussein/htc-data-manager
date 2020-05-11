package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.MaterialOfConstruction;
import kz.dilau.htcdatamanager.repository.dictionary.MaterialOfConstructionRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("MaterialOfConstruction")
public class MaterialOfConstructionDictionaryServiceImpl implements LinearDictionaryService {
    private final MaterialOfConstructionRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new MaterialOfConstruction(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        MaterialOfConstruction byId = cacheService.getById(MaterialOfConstruction.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        MaterialOfConstruction byId = cacheService.getById(MaterialOfConstruction.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private MaterialOfConstruction saveDict(MaterialOfConstruction dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
