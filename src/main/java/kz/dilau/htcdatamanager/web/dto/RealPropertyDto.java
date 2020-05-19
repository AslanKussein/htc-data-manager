package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import lombok.*;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RealPropertyDto", description = "Модель сущности недвижимости")
public class RealPropertyDto extends AIdentifierDto {
    @ApiModelProperty(value = "")
    private BuildingDto buildingDto;
    @ApiModelProperty(value = "Кадастровый номер")
    private String cadastralNumber;
    @ApiModelProperty(value = "Номер квартиры(/буква)")
    private String apartmentNumber;

    @ApiModelProperty(value = "id метаданных")
    private Long metadataId;

    @ApiModelProperty(value = "Статус метаданных")
    private Long metadataStatusId;

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

    @ApiModelProperty(value = "Характеристики недвижимости")
    private GeneralCharacteristicsDto generalCharacteristicsDto;

    public RealPropertyDto(RealProperty realProperty) {
        this.id = realProperty.getId();
        this.buildingDto = new BuildingDto(realProperty.getBuilding());
        this.cadastralNumber = realProperty.getCadastralNumber();
        this.apartmentNumber = realProperty.getApartmentNumber();
        RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
        if (nonNull(metadata)) {
            this.metadataId = metadata.getId();
            this.metadataStatusId = metadata.getMetadataStatusId();
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
    }
}


