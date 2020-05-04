package kz.dilau.htcdatamanager.service;

public interface EntityService {
    <T> T mapRequiredEntity(Class<T> clazz, Long id);

    <T> T mapEntity(Class<T> clazz, Long id);
}
