package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.web.rest.vm.ApplicationDto;

public interface ApplicationConverter {
    Application convertFromDto(ApplicationDto dto);
}
