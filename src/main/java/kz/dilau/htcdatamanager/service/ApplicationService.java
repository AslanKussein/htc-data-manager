package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationDto;

public interface ApplicationService {
    ApplicationDto getById(final String token, Long id);

    Long update(String token, Long id, ApplicationDto input);

    Long deleteById(String token, Long id);

    Long save(ApplicationDto dto);
}
