package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;

public interface ApplicationClientAutoCreateService {

    ApplicationClientDTO create(Long targetApplicationId);

    ApplicationClientDTO update(Long id, Long targetApplicationId);

}