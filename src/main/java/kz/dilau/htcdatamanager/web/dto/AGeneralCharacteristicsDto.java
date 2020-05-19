package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class AGeneralCharacteristicsDto extends AIdentifierDto {
    @ApiModelProperty(value = "Материал постройки")
    protected Long materialOfConstructionId;
    @ApiModelProperty(value = "Консьерж")
    protected Boolean concierge;
    @ApiModelProperty(value = "Колясочная")
    protected Boolean wheelchair;
    @ApiModelProperty(value = "Двор(закрытый/открытый)")
    protected Long yardTypeId;
    @ApiModelProperty(value = "Детская площадка")
    protected Boolean playground;

    @ApiModelProperty(value = "Тип лифта(мультивыбор)")
    protected List<Long> typeOfElevatorList;

    @ApiModelProperty(value = "ID вида паркинга (мультивыбор)")
    protected List<Long> parkingTypeIds;

}
