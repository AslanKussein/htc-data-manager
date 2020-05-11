package kz.dilau.htcdatamanager.service;

import org.springframework.stereotype.Service;

@Service
public interface DictionaryServiceFactory {
    LinearDictionaryService getService(String serviceType);
}
