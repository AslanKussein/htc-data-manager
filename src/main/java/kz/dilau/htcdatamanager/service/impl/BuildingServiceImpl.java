package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Building;
import kz.dilau.htcdatamanager.repository.BuildingRepository;
import kz.dilau.htcdatamanager.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;

    @Override
    public Building getByPostcode(String postcode) {
        if (isNull(postcode)) {
            return null;
        }
        return buildingRepository.getByPostcodeAndIsRemovedFalse(postcode);
    }
}
