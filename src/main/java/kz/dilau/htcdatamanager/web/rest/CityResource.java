package kz.dilau.htcdatamanager.web.rest;

import kz.dilau.htcdatamanager.service.dictionary.CityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionaries/cities")
public class CityResource extends AbstractDictionaryResource<CityManager> {
    @Autowired
    public CityResource(CityManager service) {
        super(service);
    }
}
