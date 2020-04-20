package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;

import java.util.List;

public interface ApplicationManager2 {
    ApplicationDto getApplicationById(String token, Long id);

    Long saveApplication(String token, ApplicationType type, ApplicationDto dto);

    void updateApplication(String token, Long id, ApplicationDto application);

    List<RecentlyCreatedApplication> getRecentlyCreatedApplications(final String token);

    void deleteApplicationById(String token, Long id);


}
