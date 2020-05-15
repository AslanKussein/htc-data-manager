package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.web.dto.ClientDto;

public interface ClientService {
    ClientDto findClientByPhoneNumber(String phoneNumber);

    ClientDto getById(String token, Long id);

    ClientDto save(ClientDto dto);

    ClientDto update(String token, Long id, ClientDto dto);

    ClientDto deleteById(String token, Long id);

    Client getClientById(Long id);

    /**
     * edit info about Client;
     */
    ClientDto update(ClientDto dto);
}
