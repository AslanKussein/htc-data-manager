package kz.dilau.htcdatamanager.util;

import kz.dilau.htcdatamanager.domain.*;
import kz.dilau.htcdatamanager.domain.dictionary.*;
import kz.dilau.htcdatamanager.service.BuildingService;
import kz.dilau.htcdatamanager.service.EntityService;
import kz.dilau.htcdatamanager.web.dto.*;
import kz.dilau.htcdatamanager.web.dto.client.ApplicationClientDTO;
import kz.dilau.htcdatamanager.web.dto.client.PurchaseInfoClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class EntityMappingTool {
    private final EntityService entityService;

    public ApplicationPurchaseData convertApplicationPurchaseData(ApplicationDto dto) {
        ApplicationPurchaseDataDto dataDto = dto.getPurchaseDataDto();
        return new ApplicationPurchaseData(dataDto, entityService.mapRequiredEntity(City.class, dataDto.getCityId()),
                entityService.mapRequiredEntity(District.class, dataDto.getDistrictId()));
    }

   /* public ApplicationPurchaseData convertApplicationClientPurchaseData(ApplicationClientDTO dto) {
        ApplicationPurchaseDataDto dataDto = dto.get();
        return new ApplicationPurchaseData(dataDto, entityService.mapRequiredEntity(City.class, dataDto.getCityId()),
                entityService.mapRequiredEntity(District.class, dataDto.getDistrictId()));
    }*/

    public PurchaseInfo convertPurchaseInfo(ApplicationDto dto) {
        if (nonNull(dto) && nonNull(dto.getPurchaseDataDto()) && nonNull(dto.getPurchaseInfoDto())) {
            PurchaseInfoDto infoDto = dto.getPurchaseInfoDto();
            return new PurchaseInfo(infoDto, dto.getPurchaseDataDto().getObjectPricePeriod(),
                    entityService.mapEntity(MaterialOfConstruction.class, infoDto.getMaterialOfConstructionId()),
                    entityService.mapEntity(YardType.class, infoDto.getYardTypeId()));
        }
        return null;
    }

    public PurchaseInfo convertClientPurchaseInfo(ApplicationClientDTO dto) {
        if (nonNull(dto) && nonNull(dto.getPurchaseInfoDto()) && nonNull(dto.getPurchaseInfoDto())) {
            PurchaseInfoClientDto infoDto = dto.getPurchaseInfoDto();
            return new PurchaseInfo(infoDto, dto.getPurchaseInfoDto().getObjectPricePeriod());
        }
        return null;
    }

    public Building convertBuilding(BuildingDto buildingDto) {
        return new Building(buildingDto,
                entityService.mapRequiredEntity(City.class, buildingDto.getCityId()),
                entityService.mapRequiredEntity(District.class, buildingDto.getDistrictId()),
                entityService.mapRequiredEntity(Street.class, buildingDto.getStreetId()));
    }

    public RealPropertyMetadata convertRealPropertyMetadata(RealPropertyDto realPropertyDto) {
        return new RealPropertyMetadata(realPropertyDto,
                entityService.mapEntity(Sewerage.class, realPropertyDto.getSewerageId()),
                entityService.mapEntity(HeatingSystem.class, realPropertyDto.getHeatingSystemId()),
                nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(PropertyDeveloper.class, realPropertyDto.getGeneralCharacteristicsDto().getPropertyDeveloperId()) : null,
                nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(HouseCondition.class, realPropertyDto.getGeneralCharacteristicsDto().getHouseConditionId()) : null,
                nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(MaterialOfConstruction.class, realPropertyDto.getGeneralCharacteristicsDto().getMaterialOfConstructionId()) : null,
                nonNull(realPropertyDto.getGeneralCharacteristicsDto()) ? entityService.mapEntity(YardType.class, realPropertyDto.getGeneralCharacteristicsDto().getYardTypeId()) : null);
    }

    public ApplicationSellData convertApplicationSellData(ApplicationDto dto) {
        Building building = convertBuilding(dto.getRealPropertyDto().getBuildingDto());
        RealPropertyMetadata metadata = convertRealPropertyMetadata(dto.getRealPropertyDto());
        ApplicationSellDataDto dataDto = dto.getSellDataDto();
        RealProperty realProperty = new RealProperty(dto.getRealPropertyDto(), building, metadata);
        ApplicationSellData sellData = new ApplicationSellData(dataDto);
        sellData.setRealProperty(realProperty);
        return sellData;
    }


}
