package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.old.OldGeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.dictionary.ParkingTypeRepository;
import kz.dilau.htcdatamanager.repository.dictionary.OldResidentialComplexRepository;
import kz.dilau.htcdatamanager.repository.dictionary.TypeOfElevatorRepository;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.ResidentialComplexService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ResidentialComplexServiceImpl implements ResidentialComplexService {
    private final OldResidentialComplexRepository residentialComplexRepository;
    private final TypeOfElevatorRepository typeOfElevatorRepository;
    private final ParkingTypeRepository parkingTypeRepository;
    private final EntityService entityService;

    @Override
    public ResidentialComplexDto getById(Long id) {
        OldResidentialComplex residentialComplex = getResidentialComplexById(id);
        return new ResidentialComplexDto(residentialComplex);
    }

    @Override
    public List<ResidentialComplexDto> getAll() {
        return residentialComplexRepository
                .findAllByRemovedFalse()
                .stream()
                .map(ResidentialComplexDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public PageDto<ResidentialComplexDto> getAllPageable(PageableDto dto) {
        List<ResidentialComplexDto> residentialComplexDtoList = new ArrayList<>();
        Page<OldResidentialComplex> residentialComplexPage = residentialComplexRepository.findPageByRemovedFalse(PageableUtils.createPageRequest(dto));
        residentialComplexPage.forEach(item -> residentialComplexDtoList.add(new ResidentialComplexDto(item)));
        return new PageDto(residentialComplexPage, residentialComplexDtoList);
    }

    @Transactional
    @Override
    public ResidentialComplexDto save(String token, ResidentialComplexDto dto) {
        return saveResidentialComplex(new OldResidentialComplex(), dto);
    }

    private ResidentialComplexDto saveResidentialComplex(OldResidentialComplex residentialComplex, ResidentialComplexDto dto) {
        OldGeneralCharacteristics generalCharacteristics = OldGeneralCharacteristics.builder()
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
                .yearOfConstruction(dto.getYearOfConstruction())
                .materialOfConstruction(entityService.mapEntity(MaterialOfConstruction.class, dto.getMaterialOfConstructionId()))
                .city(entityService.mapEntity(City.class, dto.getCityId()))
                .district(entityService.mapEntity(District.class, dto.getDistrictId()))
                .propertyDeveloper(entityService.mapEntity(PropertyDeveloper.class, dto.getPropertyDeveloperId()))
                .street(entityService.mapEntity(Street.class, dto.getStreetId()))
                .yardType(entityService.mapEntity(YardType.class, dto.getYardTypeId()))
                .build();
        if (!CollectionUtils.isEmpty(dto.getTypeOfElevatorIdList())) {
            Set<TypeOfElevator> elevators = typeOfElevatorRepository.findByIdIn(dto.getTypeOfElevatorIdList());
            generalCharacteristics.getTypesOfElevator().clear();
            generalCharacteristics.getTypesOfElevator().addAll(elevators);
        }
        if (!CollectionUtils.isEmpty(dto.getParkingTypeIds())) {
            Set<ParkingType> parkingTypes = parkingTypeRepository.findByIdIn(dto.getParkingTypeIds());
            generalCharacteristics.getParkingTypes().clear();
            generalCharacteristics.getParkingTypes().addAll(parkingTypes);
        }
        if (nonNull(residentialComplex.getId()) && nonNull(residentialComplex.getGeneralCharacteristics())) {
            generalCharacteristics.setId(residentialComplex.getGeneralCharacteristics().getId());
        }
        residentialComplex.setHouseName(dto.getHouseName());
        residentialComplex.setNumberOfEntrances(dto.getNumberOfEntrances());
        residentialComplex.setGeneralCharacteristics(generalCharacteristics);

        residentialComplex = residentialComplexRepository.save(residentialComplex);
        return new ResidentialComplexDto(residentialComplex);
    }

    @Override
    public ResidentialComplexDto update(String token, Long id, ResidentialComplexDto input) {
        OldResidentialComplex residentialComplex = getResidentialComplexById(id);
        return saveResidentialComplex(residentialComplex, input);
    }

    @Override
    public ResidentialComplexDto deleteById(String token, Long id) {
        OldResidentialComplex residentialComplex = getResidentialComplexById(id);
        residentialComplex.setIsRemoved(true);
        residentialComplex = residentialComplexRepository.save(residentialComplex);
        return new ResidentialComplexDto(residentialComplex);
    }

    private OldResidentialComplex getResidentialComplexById(Long id) {
        Optional<OldResidentialComplex> optionalResidentialComplex = residentialComplexRepository.findById(id);
        if (optionalResidentialComplex.isPresent()) {
            if (optionalResidentialComplex.get().getIsRemoved()) {
                throw EntityRemovedException.createResidentialComplexRemoved(id);
            }
            return optionalResidentialComplex.get();
        } else {
            throw NotFoundException.createResidentialComplexById(id);
        }
    }
}
