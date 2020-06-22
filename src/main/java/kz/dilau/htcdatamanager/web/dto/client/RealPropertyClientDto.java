package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyFile;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.RealPropertyFileType;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import kz.dilau.htcdatamanager.web.dto.BuildingDto;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель сущности недвижимости")
public class RealPropertyClientDto {


    @ApiModelProperty(value = "Модель здания/строения")
    private BuildingDto buildingDto;
    @ApiModelProperty(name = "totalArea", value = "Общая площадь")
    private BigDecimal totalArea;
    @ApiModelProperty(name = "objectTypeId", value = "ID типа объекта")
    private Long objectTypeId;
    @ApiModelProperty(name = "numberOfRooms", value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "apartmentNumber", value = "Номер квартиры(/буква)")
    private String apartmentNumber;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;
    @ApiModelProperty(name = "floor", value = "Этаж")
    private Integer floor;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private BigDecimal livingArea;
    @ApiModelProperty(name = "atelier", value = "Студия")
    private Boolean atelier;
    @ApiModelProperty(name = "separateBathroom", value = "Санузел раздельный")
    private Boolean separateBathroom;

    @ApiModelProperty(value = "Адрес")
    private MultiLangText address;
    @ApiModelProperty(name = "photoIdList", value = "Список ID фотографии")
    private Set<String> photoIdList;
    @ApiModelProperty(name = "housingPlanImageIdList", value = "Список ID фотографии")
    private Set<String> housingPlanImageIdList;
    @ApiModelProperty(name = "virtualTourImageIdList", value = "Список ID фотографии")
    private Set<String> virtualTourImageIdList;

    @ApiModelProperty(value = "Общая информация о сделке продажи объекта")
    private List<ApplicationSellDataDto> sellDataDtoList;


    public RealPropertyClientDto(RealProperty realProperty) {
        this.buildingDto = new BuildingDto(realProperty.getBuilding());
        this.apartmentNumber = realProperty.getApartmentNumber();
        RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
        if (nonNull(metadata)) {
            this.floor = metadata.getFloor();
            this.numberOfRooms = metadata.getNumberOfRooms();
            this.totalArea = metadata.getTotalArea();
            this.livingArea = metadata.getLivingArea();
            this.atelier = metadata.getAtelier();
            this.separateBathroom = metadata.getSeparateBathroom();
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

        this.sellDataDtoList = realProperty.getSellDataList()
                .stream()
                .map(ApplicationSellDataDto::new)
                .collect(Collectors.toList());
    }
}


