package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationFullViewDto;

public interface ApplicationFullViewService {
    ApplicationFullViewDto getById(Long id);
}
