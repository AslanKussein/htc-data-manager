package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientPayDTO;

public interface ApplicationClientPayService {

    ApplicationClientDTO update(Long id, ApplicationClientPayDTO dto);

}