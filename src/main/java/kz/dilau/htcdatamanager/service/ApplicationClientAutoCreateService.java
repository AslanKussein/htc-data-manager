package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationDto;

public interface ApplicationClientAutoCreateService {

    Long save(ApplicationDto dto);

    Long update(Long id, ApplicationDto input);

}