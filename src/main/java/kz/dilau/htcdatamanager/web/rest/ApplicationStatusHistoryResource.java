package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.config.Constants;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.ApplicationStatusHistory;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import kz.dilau.htcdatamanager.repository.ApplicationRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusHistoryRepository;
import kz.dilau.htcdatamanager.repository.ApplicationStatusRepository;
import kz.dilau.htcdatamanager.web.dto.ApplicationStatusHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.STATUS_HISTORIES_REST_ENDPOINT)
public class ApplicationStatusHistoryResource {
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusRepository applicationStatusRepository;

    @PostMapping
    ResponseEntity<Long> saveHistory(@RequestBody ApplicationStatusHistoryDto historyDto) {
        ApplicationStatusHistory history = new ApplicationStatusHistory();
        Application application = applicationRepository.getOne(historyDto.getApplicationId());
        history.setApplication(application);
        ApplicationStatus status = applicationStatusRepository.getOne(historyDto.getApplicationStatusId());
        history.setApplicationStatus(status);
        history.setComment(historyDto.getComment());
        Long id = applicationStatusHistoryRepository.save(history).getId();
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ApplicationStatusHistoryDto getById(@PathVariable Long id) {
        ApplicationStatusHistory history = applicationStatusHistoryRepository.getOne(id);
        return toDto(history);
    }

    @GetMapping
    public List<ApplicationStatusHistoryDto> getAll() {
        return applicationStatusHistoryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private ApplicationStatusHistoryDto toDto(ApplicationStatusHistory history) {
        ApplicationStatusHistoryDto dto = new ApplicationStatusHistoryDto();
        dto.setApplicationId(history.getApplication().getId());
        dto.setApplicationStatusId(history.getApplicationStatus().getId());
        dto.setComment(history.getComment());
        return dto;
    }


}
