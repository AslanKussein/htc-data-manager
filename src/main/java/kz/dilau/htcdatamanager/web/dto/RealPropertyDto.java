package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyFile;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RealPropertyDto", description = "Модель сущности недвижимости")
public class RealPropertyDto {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "")
    private BuildingDto buildingDto;
    @ApiModelProperty(value = "Кадастровый номер")
    private String cadastralNumber;
    @ApiModelProperty(value = "Номер квартиры(/буква)")
    private String apartmentNumber;

    @ApiModelProperty(value = "id метаданных")
    private Long metadataId;

    @ApiModelProperty(name = "floor", value = "Этаж")
    private Integer floor;
    @ApiModelProperty(name = "numberOfRooms", value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "numberOfBedrooms", value = "Количество спален")
    private Integer numberOfBedrooms;
    @ApiModelProperty(name = "totalArea", value = "Общая площадь")
    private BigDecimal totalArea;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private BigDecimal livingArea;
    @ApiModelProperty(name = "kitchenArea", value = "Площадь кухни")
    private BigDecimal kitchenArea;
    @ApiModelProperty(name = "balconyArea", value = "Площадь балкона")
    private BigDecimal balconyArea;
    @ApiModelProperty(name = "sewerageId", value = "Канализация")
    private Long sewerageId;
    @ApiModelProperty(name = "heatingSystemId", value = "Отопительная система")
    private Long heatingSystemId;
    @ApiModelProperty(name = "landArea", value = "Площадь участка")
    private BigDecimal landArea;
    @ApiModelProperty(value = "Студия")
    protected Boolean atelier;
    @ApiModelProperty(value = "Санузел раздельный")
    protected Boolean separateBathroom;

    @ApiModelProperty(value = "Признак редактирования")
    protected Boolean edited = false;

    @ApiModelProperty(value = "Характеристики недвижимости")
    private GeneralCharacteristicsDto generalCharacteristicsDto;

    @ApiModelProperty(value = "Признак редактирования фотографий")
    protected Boolean filesEdited = false;
    @ApiModelProperty(name = "photoIdList", value = "Список ID фотографии")
    private Set<String> photoIdList;
    @ApiModelProperty(name = "housingPlanImageIdList", value = "Список ID фотографии")
    private Set<String> housingPlanImageIdList;
    @ApiModelProperty(name = "virtualTourImageIdList", value = "Список ID фотографии")
    private Set<String> virtualTourImageIdList;

    @ApiModelProperty(value = "Адрес")
    private MultiLangText address;

    public RealPropertyDto(RealProperty realProperty) {
        this.id = realProperty.getId();
        this.buildingDto = new BuildingDto(realProperty.getBuilding());
        this.cadastralNumber = realProperty.getCadastralNumber();
        this.apartmentNumber = realProperty.getApartmentNumber();
        RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
        if (nonNull(metadata)) {
            this.metadataId = metadata.getId();
            this.floor = metadata.getFloor();
            this.numberOfRooms = metadata.getNumberOfRooms();
            this.numberOfBedrooms = metadata.getNumberOfBedrooms();
            this.totalArea = metadata.getTotalArea();
            this.livingArea = metadata.getLivingArea();
            this.kitchenArea = metadata.getKitchenArea();
            this.balconyArea = metadata.getBalconyArea();
            this.sewerageId = metadata.getSewerageId();
            this.heatingSystemId = metadata.getHeatingSystemId();
            this.landArea = metadata.getLandArea();
            this.atelier = metadata.getAtelier();
            this.separateBathroom = metadata.getSeparateBathroom();
            this.generalCharacteristicsDto = new GeneralCharacteristicsDto(metadata.getGeneralCharacteristics());
        }
        if (nonNull(realProperty.getBuilding())) {
            this.address = DictionaryMappingTool.mapAddressToMultiLang(realProperty.getBuilding(), realProperty.getApartmentNumber());
        }
        RealPropertyFile realPropertyFile = realProperty.getFileByStatus(MetadataStatus.APPROVED);
        if (nonNull(realPropertyFile)) {
            this.photoIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.PHOTO);
            this.housingPlanImageIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN);
            this.virtualTourImageIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR);
        }
    }

    public RealPropertyDto(RealProperty realProperty, Long applicationId) {
        this.buildingDto = new BuildingDto(realProperty.getBuilding());
        this.cadastralNumber = realProperty.getCadastralNumber();
        this.apartmentNumber = realProperty.getApartmentNumber();
        RealPropertyMetadata metadata = realProperty.getMetadataByStatusAndApplication(MetadataStatus.NOT_APPROVED, applicationId);
        if (nonNull(metadata)) {
            this.metadataId = metadata.getId();
            this.floor = metadata.getFloor();
            this.numberOfRooms = metadata.getNumberOfRooms();
            this.numberOfBedrooms = metadata.getNumberOfBedrooms();
            this.totalArea = metadata.getTotalArea();
            this.livingArea = metadata.getLivingArea();
            this.kitchenArea = metadata.getKitchenArea();
            this.balconyArea = metadata.getBalconyArea();
            this.sewerageId = metadata.getSewerageId();
            this.heatingSystemId = metadata.getHeatingSystemId();
            this.landArea = metadata.getLandArea();
            this.atelier = metadata.getAtelier();
            this.separateBathroom = metadata.getSeparateBathroom();
            this.generalCharacteristicsDto = new GeneralCharacteristicsDto(metadata.getGeneralCharacteristics());
        }
        RealPropertyFile realPropertyFile = realProperty.getFileByStatus(MetadataStatus.APPROVED);
        if (nonNull(realPropertyFile)) {
            this.photoIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.PHOTO);
            this.housingPlanImageIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.HOUSING_PLAN);
            this.virtualTourImageIdList = realPropertyFile.getFilesMap().get(RealPropertyFileType.VIRTUAL_TOUR);
        }
    }
}


