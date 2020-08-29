package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationViewDTO;

import java.util.List;

public interface ApplicationViewService {
    ApplicationViewDTO getById(String token, Long id);
    List<ApplicationViewDTO> getApplicationsForCompare(String token, List<Long> ids);
}
