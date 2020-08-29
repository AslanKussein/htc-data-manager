package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.common.ListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {
    ApplicationDto getById(final String token, Long id);

    Long update(String token, Long id, ApplicationDto input);

    Long deleteById(String token, Long id);

    Long save(String token, ApplicationDto dto);

    Long saveLightApplication(ApplicationLightDto dto);

    Long reassignApplication(AssignmentDto dto);

    Long approveFiles(Long applicationId, Long statusId);

    Application getApplicationById(Long id);

    MetadataWithApplicationsDto getApartmentByNumberAndPostcode(String apartmentNumber, String postcode);

    Page<ApplicationDto> getNotApprovedMetadata(Pageable pageable);

    Page<ApplicationDto> getNotApprovedFiles(Pageable pageable);

    Long approveMetadata(Long applicationId, Long statusId);

    List<String> getOperationList(String token, Application application);

    String getAppointmentAgent(String agent);

    Long approveReserve(Long applicationId);

    ListResponse<String> getOperationsByAppId(String token, Long applicationId);

    ResultDto hasActualAppByClient(String agentLogin, String clientLogin);

    ApplicationDto mapToApplicationDto(Application application, List<String> operations);
}
