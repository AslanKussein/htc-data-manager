package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.ClientApplicationCreateDTO;

import java.util.List;

public interface ApplicationClientService {
    ApplicationClientDTO getById(final String token, Long id);

    Long update(String token, Long id, ApplicationClientDTO input);

    Long deleteById(String token, Long id);

    Long save(String token, ApplicationClientDTO dto);

    List<ApplicationClientDTO> getAllMyAppByOperationTypeId(final String clientLogin, String token, Long operationTypeId);

    Long save(ClientApplicationCreateDTO dto);

    void replaceDeviceLink(String token, String login, String deviceUuid);
}

