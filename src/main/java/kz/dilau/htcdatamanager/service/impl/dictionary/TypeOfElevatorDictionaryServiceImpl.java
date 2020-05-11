package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import kz.dilau.htcdatamanager.repository.dictionary.TypeOfElevatorRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("TypeOfElevator")
public class TypeOfElevatorDictionaryServiceImpl implements LinearDictionaryService {
    private final TypeOfElevatorRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new TypeOfElevator(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        TypeOfElevator byId = cacheService.getById(TypeOfElevator.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        TypeOfElevator byId = cacheService.getById(TypeOfElevator.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private TypeOfElevator saveDict(TypeOfElevator dict, DictionaryItemRequestDto itemDto) {
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
