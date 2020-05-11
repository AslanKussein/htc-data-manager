package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.service.DictionaryCacheService;
import kz.dilau.htcdatamanager.service.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component("ApplicationStatus")
public class ApplicationStatusDictionaryServiceImpl implements LinearDictionaryService {
    private final ApplicationStatusRepository repository;
    private final DictionaryCacheService cacheService;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new ApplicationStatus(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        ApplicationStatus byId = cacheService.getById(ApplicationStatus.class, id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id) {
        ApplicationStatus byId = cacheService.getById(ApplicationStatus.class, id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    @Override
    public List childList(Long parentId) {
        return null;
    }

    private ApplicationStatus saveDict(ApplicationStatus dict, DictionaryItemRequestDto itemDto) {
        dict.setCode(itemDto.getCode());
        dict.setMultiLang(new MultiLang(itemDto));
        return dict;
    }
}
