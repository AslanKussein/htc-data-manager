package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;

import java.util.List;

public interface ApplicationManager {
    ApplicationDto getApplicationById(final String token, Long id);

    List<RecentlyCreatedApplication> getRecentlyCreatedApplications(final String token);

    Long saveApplication(final String token, ApplicationType type, ApplicationDto dto);

    void updateApplication(final String token, ApplicationDto application);

    void deleteApplication(final String token, Long id);
}
