package kz.dilau.htcdatamanager.service.impl.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.service.dictionary.LinearDictionaryService;
import kz.dilau.htcdatamanager.web.dto.dictionary.DictionaryItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component("ApplicationStatus")
public class ApplicationStatusDictionaryService implements LinearDictionaryService {
    private final ApplicationStatusRepository repository;

    @Override
    public Long save(DictionaryItemRequestDto dto) {
        return repository.save(saveDict(new ApplicationStatus(), dto)).getId();
    }

    @Override
    public Long update(Long id, DictionaryItemRequestDto dto) {
        ApplicationStatus byId = getById(id);
        return repository.save(saveDict(byId, dto)).getId();
    }

    @Override
    public Long delete(Long id, DictionaryItemRequestDto dto) {
        ApplicationStatus byId = getById(id);
        byId.setIsRemoved(true);
        return repository.save(byId).getId();
    }

    private ApplicationStatus saveDict(ApplicationStatus applicationStatus, DictionaryItemRequestDto itemDto) {
        applicationStatus.setCode(itemDto.getCode());
        applicationStatus.setMultiLang(new MultiLang(itemDto));
        return applicationStatus;
    }

    private ApplicationStatus getById(Long id) {
        if (nonNull(id)) {
            Optional<ApplicationStatus> byId = repository.findById(id);
            if (byId.isPresent()) {
                return byId.get();
            } else {
                throw NotFoundException.createEntityNotFoundById("ApplicationStatus", id);
            }
        } else {
            throw BadRequestException.idMustNotBeNull();
        }
    }
}
