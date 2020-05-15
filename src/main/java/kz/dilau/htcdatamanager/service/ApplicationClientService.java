package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;

public interface ApplicationClientService {
    ApplicationClientDTO getById(Long id);

    Long create(String login, ApplicationClientDTO dto);

    Long update(Long id, ApplicationClientDTO dto);

    Long deleteById(Long id);
}
