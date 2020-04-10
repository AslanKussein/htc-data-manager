package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityManager extends AbstractDictionaryManager<City, CityRepository> {
    @Autowired
    public CityManager(CityRepository repository) {
        super(repository);
    }
}
