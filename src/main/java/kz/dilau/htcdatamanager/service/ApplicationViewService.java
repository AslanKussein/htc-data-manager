package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationViewDTO;

public interface ApplicationViewService {
    ApplicationViewDTO getById(String token, Long id);
}
