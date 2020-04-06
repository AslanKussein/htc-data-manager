package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationType;
import kz.dilau.htcdatamanager.web.rest.vm.RecentlyCreatedApplication;

import java.util.List;

public interface ApplicationManager {
    Long saveApplication(String token, ApplicationType type, ApplicationDto dto);

    List<RecentlyCreatedApplication> getRecentlyCreatedApps(String token);

    ApplicationDto getById(String token, Long id);

    void update(Long id, ApplicationDto application);
}
