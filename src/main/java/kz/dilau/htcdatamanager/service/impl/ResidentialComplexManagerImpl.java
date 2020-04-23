package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.web.dto.errors.NotFoundException;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.repository.GeneralCharacteristicsRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ParkingTypeRepository;
import kz.dilau.htcdatamanager.repository.dictionary.ResidentialComplexRepository;
import kz.dilau.htcdatamanager.repository.dictionary.TypeOfElevatorRepository;
import kz.dilau.htcdatamanager.service.ResidentialComplexManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ResidentialComplexManagerImpl implements ResidentialComplexManager {
    private final ResidentialComplexRepository rcRepository;
    private final GeneralCharacteristicsRepository gcRepository;
    private final TypeOfElevatorRepository toeRepository;
    private final ParkingTypeRepository parkingTypeRepository;
    private final EntityManager entityManager;

    @Override
    public ResidentialComplexDto getById(String token, Long id) {
        return rcRepository
                .findById(id)
                .map(ResidentialComplexDto::new)
                .orElseThrow(() -> new NotFoundException("Residential complex with id not found: " + id));
    }

    @Override
    public List<ResidentialComplexDto> getAll(String token) {
        return rcRepository
                .findAll()
                .stream()
                .map(ResidentialComplexDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long save(String token, ResidentialComplexDto dto) {
        GeneralCharacteristics.GeneralCharacteristicsBuilder gcBuilder = GeneralCharacteristics.builder()
                .apartmentsOnTheSite(dto.getApartmentsOnTheSite())
                .ceilingHeight(dto.getCeilingHeight())
                .concierge(dto.getConcierge())
                .houseNumber(dto.getHouseNumber())
                .houseNumberFraction(dto.getHouseNumberFraction())
                .housingClass(dto.getHousingClass())
                .housingCondition(dto.getHousingCondition())
                .numberOfApartments(dto.getNumberOfApartments())
                .numberOfFloors(dto.getNumberOfFloors())
                .playground(dto.getPlayground())
                .wheelchair(dto.getWheelchair())
                .yearOfConstruction(dto.getYearOfConstruction());
        if (Objects.nonNull(dto.getMaterialOfConstructionId())) {
            MaterialOfConstruction materialOfConstruction = entityManager.getReference(MaterialOfConstruction.class, dto.getMaterialOfConstructionId());
            gcBuilder.materialOfConstruction(materialOfConstruction);
        }
        if (Objects.nonNull(dto.getCityId())) {
            City city = entityManager.getReference(City.class, dto.getCityId());
            gcBuilder.city(city);
        }
        if (Objects.nonNull(dto.getDistrictId())) {
            District district = entityManager.getReference(District.class, dto.getDistrictId());
            gcBuilder.district(district);
        }
        if (Objects.nonNull(dto.getPropertyDeveloperId())) {
            PropertyDeveloper propertyDeveloper = entityManager.getReference(PropertyDeveloper.class, dto.getPropertyDeveloperId());
            gcBuilder.propertyDeveloper(propertyDeveloper);
        }
        if (Objects.nonNull(dto.getStreetId())) {
            Street street = entityManager.getReference(Street.class, dto.getStreetId());
            gcBuilder.street(street);
        }
        if (Objects.nonNull(dto.getYardTypeId())) {
            YardType yardType = entityManager.getReference(YardType.class, dto.getYardTypeId());
            gcBuilder.yardType(yardType);
        }
        if (!CollectionUtils.isEmpty(dto.getTypeOfElevatorIdList())) {
            Set<TypeOfElevator> elevators = toeRepository.findByIdIn(dto.getTypeOfElevatorIdList());
            gcBuilder.typesOfElevator(elevators);
        }
        if (!CollectionUtils.isEmpty(dto.getParkingTypeIds())) {
            Set<ParkingType> parkingTypes = parkingTypeRepository.findByIdIn(dto.getTypeOfElevatorIdList());
            gcBuilder.parkingTypes(parkingTypes);
        }
        GeneralCharacteristics generalCharacteristics = gcRepository.save(gcBuilder.build());
        ResidentialComplex rc = ResidentialComplex.builder()
                .houseName(dto.getHouseName())
                .numberOfEntrances(dto.getNumberOfEntrances())
                .generalCharacteristics(generalCharacteristics)
                .build();
        Long id = rcRepository.save(rc).getId();
        return id;
    }

    @Override
    public void update(String token, Long id, ResidentialComplexDto input) {
        ResidentialComplex residentialComplex = rcRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Residential complex with id %s not found", id)));


    }

    @Override
    public void deleteById(String token, Long id) {
        boolean exists = rcRepository.existsById(id);
        if (!exists) throw new NotFoundException("Residential complex with id not found: " + id);
        rcRepository.deleteById(id);
    }
}
