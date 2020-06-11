package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;

public interface ApplicationClientService {
    ApplicationClientDTO getById(final String token, Long id);

    Long update(String token, Long id, ApplicationClientDTO input);

    Long deleteById(String token, Long id);

    Long save(String token, ApplicationClientDTO dto);
}

