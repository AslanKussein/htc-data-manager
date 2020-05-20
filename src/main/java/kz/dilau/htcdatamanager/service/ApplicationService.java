package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.web.dto.*;

import java.util.List;

public interface ApplicationService {
    ApplicationDto getById(final String token, Long id);

    Long update(String token, Long id, ApplicationDto input);

    Long deleteById(String token, Long id);

    Long save(ApplicationDto dto);

    Long saveLightApplication(ApplicationLightDto dto);

    Long reassignApplication(AssignmentDto dto);

    Long changeStatus(ChangeStatusDto dto);

    Application getApplicationById(Long id);

    List<ApplicationByRealPropertyDto> getApartmentByNumberAndPostcode(String apartmentNumber, String postcode);
}
