package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationLightDto;
import kz.dilau.htcdatamanager.web.dto.AssignmentDto;

public interface ApplicationService {
    ApplicationDto getById(final String token, Long id);

    Long update(String token, Long id, ApplicationDto input);

    Long deleteById(String token, Long id);

    Long save(ApplicationDto dto);

    Long saveLightApplication(ApplicationLightDto dto);

    Long reassignApplication(AssignmentDto dto);
}
