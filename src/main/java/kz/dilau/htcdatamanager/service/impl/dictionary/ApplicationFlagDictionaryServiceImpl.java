package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationFlag;
import kz.dilau.htcdatamanager.domain.dictionary.OperationType;
import kz.dilau.htcdatamanager.repository.dictionary.ApplicationFlagRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("ApplicationFlag")
public class ApplicationFlagDictionaryServiceImpl implements LinearDictionaryService {
    private final ApplicationFlagRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new ApplicationFlag(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        ApplicationFlag byId = cacheService.getById(ApplicationFlag.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        ApplicationFlag byId = cacheService.getById(ApplicationFlag.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return repository.findAllByParentIdAndIsRemovedFalse(parentId);
    }

    private ApplicationFlag saveDict(ApplicationFlag dict, DictionaryItemRequestDto itemDto) {
        dict.setOperationType(cacheService.getById(OperationType.class, itemDto.getParentId()));
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
