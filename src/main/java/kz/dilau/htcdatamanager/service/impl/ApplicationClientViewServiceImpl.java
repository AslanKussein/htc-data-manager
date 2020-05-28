package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyFile;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.service.ApplicationClientViewService;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationClientViewServiceImpl implements ApplicationClientViewService {
    private final ApplicationService applicationService;

    @Override
    public ApplicationDto getById(Long id) {
        Application application = applicationService.getApplicationById(id);
        return mapToApplicationDto(application);
    }

    private ApplicationDto mapToApplicationDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setAgent(application.getCurrentAgent());
        dto.setObjectTypeId(application.getObjectTypeId());

        RealPropertyDto realPropertyDto = new RealPropertyDto();
        RealProperty realProperty = application.getApplicationSellData().getRealProperty();
        RealPropertyFile realPropertyFile = realProperty.getFileByStatus(MetadataStatus.APPROVED);
        realPropertyDto.setBuildingDto(new BuildingDto(realProperty.getBuilding()));
        RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
        dto.setSellDataDto(new ApplicationSellDataDto(application.getApplicationSellData()));

        realPropertyDto.setId(realProperty.getId());
        realPropertyDto.setPhotoIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.PHOTO));
        realPropertyDto.setHousingPlanImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN));
        realPropertyDto.setVirtualTourImageIdList(realPropertyFile.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR));
        realPropertyDto.setMetadataId(metadata.getId());
        realPropertyDto.setFloor(metadata.getFloor());
        realPropertyDto.setNumberOfRooms(metadata.getNumberOfRooms());
        realPropertyDto.setNumberOfBedrooms(metadata.getNumberOfBedrooms());
        realPropertyDto.setTotalArea(metadata.getTotalArea());
        realPropertyDto.setLivingArea(metadata.getLivingArea());
        realPropertyDto.setKitchenArea(metadata.getKitchenArea());
        realPropertyDto.setBalconyArea(metadata.getBalconyArea());
        realPropertyDto.setSewerageId(metadata.getSewerageId());
        realPropertyDto.setHeatingSystemId(metadata.getHeatingSystemId());
        realPropertyDto.setLandArea(metadata.getLandArea());
        realPropertyDto.setAtelier(metadata.getAtelier());
        realPropertyDto.setSeparateBathroom(metadata.getSeparateBathroom());
        realPropertyDto.setGeneralCharacteristicsDto(new GeneralCharacteristicsDto(metadata.getGeneralCharacteristics()));
        dto.setRealPropertyDto(realPropertyDto);
        realPropertyDto.setApartmentNumber(realProperty.getApartmentNumber());
        dto.setClientLogin(application.getClientLogin());


        return dto;
    }

}
