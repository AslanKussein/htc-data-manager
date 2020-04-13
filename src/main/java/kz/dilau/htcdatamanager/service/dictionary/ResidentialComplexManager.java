package kz.dilau.htcdatamanager.service.dictionary;

import kz.dilau.htcdatamanager.domain.dictionary.Country;
import kz.dilau.htcdatamanager.domain.dictionary.PropertyDeveloper;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import kz.dilau.htcdatamanager.repository.dictionary.CountryRepository;
import kz.dilau.htcdatamanager.repository.dictionary.PropertyDeveloperRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ResidentialComplexRepository;
import kz.dilau.htcdatamanager.web.rest.vm.ResidentialComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ResidentialComplexManager {
    private final ResidentialComplexRepository repository;
    private final CountryRepository countryRepository;
    private final PropertyDeveloperRepository propertyDeveloperRepository;

    public List<ResidentialComplexDto> findAll() {
        return repository.findAll().stream().map(e -> toDto(e)).collect(Collectors.toList());
    }


    private ResidentialComplexDto toDto(ResidentialComplex e) {
        ResidentialComplexDto dto = new ResidentialComplexDto();
        dto.setId(e.getId());
        dto.setCountryId(e.getCountry().getId());
        dto.setHouseName(e.getHouseName());
        dto.setHousingClass(e.getHousingClass());
        dto.setHousingCondition(e.getHousingCondition());
        dto.setNumberOfApartments(e.getNumberOfApartments());
        dto.setNumberOfEntrances(e.getNumberOfEntrances());
        dto.setPropertyDeveloperId(e.getPropertyDeveloper().getId());
        return dto;
    }

    public ResidentialComplexDto getOne(Long id) {
        ResidentialComplex residentialComplex = repository.getOne(id);
        return toDto(residentialComplex);
    }


    public Long save(ResidentialComplexDto dto) {
        ResidentialComplex complex = new ResidentialComplex();
        Country country = countryRepository.getOne(dto.getCountryId());
        if (country != null) {
            complex.setCountry(country);
        }
        PropertyDeveloper developer = propertyDeveloperRepository.getOne(dto.getPropertyDeveloperId());
        if (developer != null) {
            complex.setPropertyDeveloper(developer);
        }
        complex.setHouseName(dto.getHouseName());
        complex.setHousingClass(dto.getHousingClass());
        complex.setHousingCondition(dto.getHousingCondition());
        complex.setNumberOfApartments(dto.getNumberOfApartments());
        complex.setNumberOfEntrances(dto.getNumberOfEntrances());
        return repository.save(complex).getId();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void update(Long id, ResidentialComplexDto dto) {
        ResidentialComplex complex = repository.getOne(id);
        Country country = countryRepository.getOne(dto.getCountryId());
        if (country != null) {
            complex.setCountry(country);
        }
        PropertyDeveloper developer = propertyDeveloperRepository.getOne(dto.getPropertyDeveloperId());
        if (developer != null) {
            complex.setPropertyDeveloper(developer);
        }
        complex.setHouseName(dto.getHouseName());
        complex.setHousingClass(dto.getHousingClass());
        complex.setHousingCondition(dto.getHousingCondition());
        complex.setNumberOfApartments(dto.getNumberOfApartments());
        complex.setNumberOfEntrances(dto.getNumberOfEntrances());
        repository.save(complex);
    }
}
