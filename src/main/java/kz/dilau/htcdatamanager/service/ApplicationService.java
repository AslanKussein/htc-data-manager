package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {
    ApplicationDto getById(final String token, Long id);

    Long update(String token, Long id, ApplicationDto input);

    Long deleteById(String token, Long id);

    Long save(ApplicationDto dto);

    Long saveLightApplication(ApplicationLightDto dto);

    Long reassignApplication(AssignmentDto dto);

    Long approveFiles(Long applicationId, Long statusId);

    Long changeStatus(ChangeStatusDto dto);

    Application getApplicationById(Long id);

    MetadataWithApplicationsDto getApartmentByNumberAndPostcode(String apartmentNumber, String postcode);

    Page<ApplicationDto> getNotApprovedMetadata(Pageable pageable);

    Page<ApplicationDto> getNotApprovedFiles(Pageable pageable);

    Long approveMetadata(Long applicationId, Long statusId);
}
