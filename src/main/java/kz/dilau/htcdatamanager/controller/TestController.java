package kz.dilau.htcdatamanager.controller;

import kz.dilau.htcdatamanager.domain.enums.Gender;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyOwner;
import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.repository.RealPropertyOwnerRepository;
import kz.dilau.htcdatamanager.repository.dictionary.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private RealPropertyOwnerRepository ownerRepository;

    @GetMapping("")
    public ResponseEntity<Long> createCity() {
        City city = new City();
//        city.setCode("test code");
        MultiLang multiLang = new MultiLang();
        multiLang.setNameKz("namekz");
        multiLang.setNameEn("nameen");
        multiLang.setNameRu("nameru");
        city.setMultiLang(multiLang);
        City saved = cityRepository.save(city);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> all = cityRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/client")
    public Long createClient() {
        RealPropertyOwner owner = new RealPropertyOwner();
        owner.setId(1L);
        owner.setFirstName("batman");
        owner.setSurname("wayne");
        owner.setEmail("a.tygynbayev@gmail.com");
        owner.setPatronymic("turaruly");
        owner.setGender(Gender.MALE);
        owner.setPhoneNumber("");
        return ownerRepository.save(owner).getId();
    }

    @GetMapping("/fields")
    public void fields() {
        Field[] allFields = RealProperty.class.getDeclaredFields();
        List<String> collect = Stream.of(allFields).map(Field::getName).sorted().collect(toList());
        collect.forEach(System.out::println);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        Stream
                .of(ResidentialComplex.class.getDeclaredFields())
                .map(Field::getName)
                .sorted()
                .collect(toList())
                .forEach(System.out::println);
    }
}
