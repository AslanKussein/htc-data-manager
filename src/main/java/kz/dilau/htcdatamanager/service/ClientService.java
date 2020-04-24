package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.web.dto.ClientDto;

public interface ClientService extends CommonService<Long, ClientDto, ClientDto> {
    ClientDto findClientByPhoneNumber(String phoneNumber);

    Client getClientById(Long id);
}
