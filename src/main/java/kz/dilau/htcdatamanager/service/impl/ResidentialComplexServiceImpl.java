package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Building;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.IdItem;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.dictionary.ResidentialComplexRepository;
import kz.dilau.htcdatamanager.service.BuildingService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.service.ResidentialComplexService;
import kz.dilau.htcdatamanager.util.PageableUtils;
import kz.dilau.htcdatamanager.web.dto.BuildingDto;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import kz.dilau.htcdatamanager.web.dto.common.PageDto;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ResidentialComplexServiceImpl implements ResidentialComplexService {
    private final ResidentialComplexRepository residentialComplexRepository;
    private final EntityService entityService;
    private final BuildingService buildingService;

    @Override
    public ResidentialComplexDto getById(Long id) {
        ResidentialComplex residentialComplex = getResidentialComplexById(id);
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
        Page<ResidentialComplex> residentialComplexPage = residentialComplexRepository.findPageByRemovedFalse(PageableUtils.createPageRequest(dto));
        residentialComplexPage.forEach(item -> residentialComplexDtoList.add(new ResidentialComplexDto(item)));
        return new PageDto(residentialComplexPage, residentialComplexDtoList);
    }

    @Transactional
    @Override
    public ResidentialComplexDto save(String token, ResidentialComplexDto dto) {
        return saveResidentialComplex(new ResidentialComplex(), dto);
    }

    private ResidentialComplexDto saveResidentialComplex(ResidentialComplex residentialComplex, ResidentialComplexDto dto) {
        if (isNull(dto.getBuildingDto())) {
            throw BadRequestException.createRequiredIsEmpty("Building");
        }
        BuildingDto buildingDto = dto.getBuildingDto();
        Building building = buildingService.getByPostcode(buildingDto.getPostcode());
        if (isNull(building)) {
            building = Building.builder()
                    .postcode(buildingDto.getPostcode())
                    .city(entityService.mapRequiredEntity(City.class, buildingDto.getCityId()))
                    .district(entityService.mapRequiredEntity(District.class, buildingDto.getDistrictId()))
                    .street(entityService.mapRequiredEntity(Street.class, buildingDto.getStreetId()))
                    .houseNumber(buildingDto.getHouseNumber())
                    .houseNumberFraction(buildingDto.getHouseNumberFraction())
                    .latitude(buildingDto.getLatitude())
                    .longitude(buildingDto.getLongitude())
                    .build();
        } else if (nonNull(building.getResidentialComplex()) && (isNull(residentialComplex.getId()) || !residentialComplex.getId().equals(building.getResidentialComplex().getId()))) {
            throw BadRequestException.createResidentialComplexHasFounded();
        }
        GeneralCharacteristics generalCharacteristics = GeneralCharacteristics.builder()
                .apartmentsOnTheSite(dto.getApartmentsOnTheSite())
                .ceilingHeight(dto.getCeilingHeight())
                .propertyDeveloper(entityService.mapEntity(PropertyDeveloper.class, dto.getPropertyDeveloperId()))
                .housingClass(dto.getHousingClass())
                .houseCondition(entityService.mapEntity(HouseCondition.class, dto.getHousingConditionId()))
                .numberOfFloors(dto.getNumberOfFloors())
                .numberOfApartments(dto.getNumberOfApartments())
                .yearOfConstruction(dto.getYearOfConstruction())
                .build();
        generalCharacteristics.setPlayground(dto.getPlayground());
        generalCharacteristics.setWheelchair(dto.getWheelchair());
        generalCharacteristics.setMaterialOfConstruction(entityService.mapEntity(MaterialOfConstruction.class, dto.getMaterialOfConstructionId()));
        generalCharacteristics.setYardType(entityService.mapEntity(YardType.class, dto.getYardTypeId()));
        if (!CollectionUtils.isEmpty(dto.getTypeOfElevatorIdList())) {
            generalCharacteristics.setTypesOfElevator(new HashSet<>(dto.getTypeOfElevatorIdList().stream().map(IdItem::new).collect(Collectors.toList())));
        }
        if (!CollectionUtils.isEmpty(dto.getParkingTypeIds())) {
            generalCharacteristics.setTypesOfElevator(new HashSet<>(dto.getParkingTypeIds().stream().map(IdItem::new).collect(Collectors.toList())));
        }
        if (nonNull(residentialComplex.getId())) {
            if (nonNull(residentialComplex.getGeneralCharacteristics())) {
                generalCharacteristics.setId(residentialComplex.getGeneralCharacteristics().getId());
            }
//            if (nonNull(residentialComplex.getBuilding())) {
//                building.setId(residentialComplex.getBuilding().getId());
//            }
        }
        residentialComplex.setHouseName(dto.getHouseName());
        residentialComplex.setNumberOfEntrances(dto.getNumberOfEntrances());
        residentialComplex.setGeneralCharacteristics(generalCharacteristics);
        residentialComplex.setBuilding(building);

        residentialComplex = residentialComplexRepository.save(residentialComplex);
        return new ResidentialComplexDto(residentialComplex);
    }

    @Override
    public ResidentialComplexDto update(String token, Long id, ResidentialComplexDto input) {
        ResidentialComplex residentialComplex = getResidentialComplexById(id);
        return saveResidentialComplex(residentialComplex, input);
    }

    @Override
    public ResidentialComplexDto deleteById(String token, Long id) {
        ResidentialComplex residentialComplex = getResidentialComplexById(id);
        residentialComplex.setIsRemoved(true);
        residentialComplex.setBuilding(null);
        residentialComplex = residentialComplexRepository.save(residentialComplex);
        return new ResidentialComplexDto(residentialComplex);
    }

    @Override
    public ResidentialComplexDto getByPostcode(String postcode) {
        Building building = buildingService.getByPostcode(postcode);
        if (nonNull(building) && nonNull(building.getResidentialComplex())) {
            return new ResidentialComplexDto(building.getResidentialComplex());
        } else {
            return null;
        }
    }

    private ResidentialComplex getResidentialComplexById(Long id) {
        Optional<ResidentialComplex> optionalResidentialComplex = residentialComplexRepository.findById(id);
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
