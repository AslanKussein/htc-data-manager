package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.IdItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "GeneralCharacteristicsDto", description = "Характеристики недвижимости")
public class GeneralCharacteristicsDto extends AGeneralCharacteristicsDto {
//    @ApiModelProperty(name = "residentialComplexId", value = "ID жилого комплекса")
//    private Long residentialComplexId;
    @ApiModelProperty(name = "propertyDeveloperId", value = "Застройщик")
    private Long propertyDeveloperId;
    @ApiModelProperty(name = "housingClass", value = "Класс жилья")
    private String housingClass;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;//residentialComplex
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;//residentialComplex
    @ApiModelProperty(name = "numberOfApartments", value = "Общее количество квартир")
    private Integer numberOfApartments;
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Количество квартир на площадке")
    private Integer apartmentsOnTheSite;//residentialComplex
    @ApiModelProperty(name = "ceilingHeight", value = "Высота потолков")
    private BigDecimal ceilingHeight;
    @ApiModelProperty(value = "Состояние жилья")
    private Long houseConditionId;

    public GeneralCharacteristicsDto(GeneralCharacteristics generalCharacteristics) {
        this.id = generalCharacteristics.getId();
        this.propertyDeveloperId = generalCharacteristics.getPropertyDeveloperId();
        this.housingClass = generalCharacteristics.getHousingClass();
        this.yearOfConstruction = generalCharacteristics.getYearOfConstruction();
        this.numberOfFloors = generalCharacteristics.getNumberOfFloors();
        this.numberOfApartments = generalCharacteristics.getNumberOfApartments();
        this.apartmentsOnTheSite = generalCharacteristics.getApartmentsOnTheSite();
        this.ceilingHeight = generalCharacteristics.getCeilingHeight();
        this.houseConditionId = generalCharacteristics.getHouseConditionId();
        this.materialOfConstructionId = generalCharacteristics.getMaterialOfConstructionId();
        this.concierge = generalCharacteristics.getConcierge();
        this.wheelchair = generalCharacteristics.getWheelchair();
        this.yardTypeId = generalCharacteristics.getYardTypeId();
        this.playground = generalCharacteristics.getPlayground();
        this.typeOfElevatorList = generalCharacteristics.getTypesOfElevator().stream().map(IdItem::getId).collect(Collectors.toList());
        this.parkingTypeIds = generalCharacteristics.getParkingTypes().stream().map(IdItem::getId).collect(Collectors.toList());
    }
}


