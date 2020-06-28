package kz.dilau.htcdatamanager.service;


public interface ApplicationClientAutoCreateService {

    Long create(Long targetApplicationId);

    Long update(Long id, Long targetApplicationId);

}