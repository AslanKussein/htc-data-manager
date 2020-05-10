package kz.dilau.htcdatamanager.service.dictionary;

import org.springframework.stereotype.Service;

@Service
public interface DictionaryServiceFactory {
    LinearDictionaryService getService(String serviceType);
}
