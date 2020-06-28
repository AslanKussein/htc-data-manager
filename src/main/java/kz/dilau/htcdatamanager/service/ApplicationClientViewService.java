package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientViewDto;

public interface ApplicationClientViewService {
    ApplicationClientViewDto getById(Long id);
}
