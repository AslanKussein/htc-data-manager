package kz.dilau.htcdatamanager.component.dictionary.residentialcomplex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel(description = "Модель для справочника Жилой комплекс")
@Getter
@Setter
public class ResidentialComplexDto {
    @ApiModelProperty(value = "ID жилого комплекса")
    private Long id;
//    @ApiModelProperty(value = "ID страны")
//    private Long countryId;
//    @ApiModelProperty(value = "ID города")
//    private Long cityId;
    @ApiModelProperty(value = "ID района")
    private Long districtId;
    @ApiModelProperty(value = "ID улицы")
    private Long streetId;
    @ApiModelProperty(value = "Номер здания(дома)")
    private Integer houseNumber;
    @ApiModelProperty(value = "Номер дома(дробь/буква/строение)")
    private String houseNumberFraction;
    @ApiModelProperty(value = "Название дома(ЖК)")
    private String houseName;
    @ApiModelProperty(value = "Год постройки")
    private Integer yearOfConstruction;
    @ApiModelProperty(value = "ID застройщика")
    private Long propertyDeveloperId;
    @ApiModelProperty(value = "Этажность дома")
    private Integer numberOfFloors;
    @ApiModelProperty(value = "Количество подъездов")
    private Integer numberOfEntrances;
    @ApiModelProperty(value = "Количество квартир")
    private Integer numberOfApartments;
    @ApiModelProperty(value = "Количество квартир на площадке")
    private String apartmentsOnTheSite;
    @ApiModelProperty(value = "Высота потолков")
    private Double ceilingHeight;
    @ApiModelProperty(value = "ID материала постройки")
    private Long materialOfConstructionId;
    @ApiModelProperty(value = "Класс жилья")
    private String housingClass;
    @ApiModelProperty(value = "Состояние жилья")
    private String housingCondition;
    @ApiModelProperty(value = "Тип лифта(список)")
    private List<Long> typeOfElevatorIdList;
    @ApiModelProperty(value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(value = "ID типа парковки")
    private Long parkingTypeId;
    @ApiModelProperty(value = "ID типа двора")
    private Long yardTypeId;
    @ApiModelProperty(value = "Детская площадка")
    private Boolean playground;
}
