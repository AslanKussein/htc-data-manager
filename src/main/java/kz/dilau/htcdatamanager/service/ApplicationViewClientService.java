package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.ApplicationViewClientDTO;

public interface ApplicationViewClientService {

    ApplicationViewClientDTO getByIdForClient(Long id);
}
