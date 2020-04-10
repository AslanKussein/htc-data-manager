package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.Country;
import kz.dilau.htcdatamanager.repository.dictionary.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryManager extends AbstractDictionaryManager<Country, CountryRepository> {
    @Autowired
    public CountryManager(CountryRepository repository) {
        super(repository);
    }
}
