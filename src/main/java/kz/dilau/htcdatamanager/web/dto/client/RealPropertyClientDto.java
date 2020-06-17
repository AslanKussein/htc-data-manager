package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.web.dto.BuildingDto;
import lombok.*;

import java.math.BigDecimal;

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


    public RealPropertyClientDto(RealProperty realProperty, Long applicationId) {
        this.buildingDto = new BuildingDto(realProperty.getBuilding());
        this.apartmentNumber = realProperty.getApartmentNumber();
        RealPropertyMetadata metadata = realProperty.getMetadataByStatusAndApplication(MetadataStatus.APPROVED, applicationId);
        if (nonNull(metadata)) {
            this.floor = metadata.getFloor();
            this.numberOfRooms = metadata.getNumberOfRooms();
            this.totalArea = metadata.getTotalArea();
            this.livingArea = metadata.getLivingArea();
            this.atelier = metadata.getAtelier();
            this.separateBathroom = metadata.getSeparateBathroom();
        }
    }
}


