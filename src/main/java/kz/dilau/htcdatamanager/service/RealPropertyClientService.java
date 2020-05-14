package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationClientViewDto;

public interface RealPropertyClientService {
    ApplicationClientViewDto getById(Long id);
}
