package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationDto;

public interface ApplicationClientViewService {
    ApplicationDto getById(Long id);
}
