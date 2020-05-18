package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Building;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.IdItem;
import kz.dilau.htcdatamanager.domain.dictionary.ResidentialComplex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "ResidentialComplexDto", description = "Модель для справочника Жилой комплекс")
public class ResidentialComplexDto {
    @ApiModelProperty(value = "ID жилого комплекса")
    private Long id;
    @ApiModelProperty(value = "Здание", required = true)
    private BuildingDto buildingDto;
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
    private Integer apartmentsOnTheSite;
    @ApiModelProperty(value = "Высота потолков")
    private BigDecimal ceilingHeight;
    @ApiModelProperty(value = "ID материала постройки")
    private Long materialOfConstructionId;
    @ApiModelProperty(value = "Класс жилья")
    private String housingClass;
    @ApiModelProperty(value = "Состояние жилья")
    private Long housingConditionId;
    @ApiModelProperty(value = "Тип лифта(список)")
    private List<Long> typeOfElevatorIdList;
    @ApiModelProperty(value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(value = "ID типа парковки")
    private List<Long> parkingTypeIds;
    @ApiModelProperty(value = "ID типа двора")
    private Long yardTypeId;
    @ApiModelProperty(value = "Детская площадка")
    private Boolean playground;

    public ResidentialComplexDto(ResidentialComplex residentialComplex) {
        this.id = residentialComplex.getId();
        this.houseName = residentialComplex.getHouseName();
        this.numberOfEntrances = residentialComplex.getNumberOfEntrances();
        if (Objects.nonNull(residentialComplex.getGeneralCharacteristics())) {
            GeneralCharacteristics gc = residentialComplex.getGeneralCharacteristics();
            this.apartmentsOnTheSite = gc.getApartmentsOnTheSite();
            this.ceilingHeight = gc.getCeilingHeight();
            this.concierge = gc.getConcierge();
            this.housingClass = gc.getHousingClass();
            this.housingConditionId = gc.getHouseCondition().getId();
            this.numberOfApartments = gc.getNumberOfApartments();
            this.numberOfFloors = gc.getNumberOfFloors();
            this.playground = gc.getPlayground();
            this.wheelchair = gc.getWheelchair();
            this.yearOfConstruction = gc.getYearOfConstruction();
            if (Objects.nonNull(gc.getYardType())) {
                this.yardTypeId = gc.getYardType().getId();
            }
            if (Objects.nonNull(gc.getMaterialOfConstruction())) {
                this.materialOfConstructionId = gc.getMaterialOfConstruction().getId();
            }
            if (Objects.nonNull(gc.getPropertyDeveloper())) {
                this.propertyDeveloperId = gc.getPropertyDeveloper().getId();
            }
            if (!CollectionUtils.isEmpty(gc.getTypesOfElevator())) {
                this.typeOfElevatorIdList = gc
                        .getTypesOfElevator()
                        .stream()
                        .map(IdItem::getId)
                        .collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(gc.getParkingTypes())) {
                this.parkingTypeIds = gc
                        .getParkingTypes()
                        .stream()
                        .map(IdItem::getId)
                        .collect(Collectors.toList());
            }
        }
        if (Objects.nonNull(residentialComplex.getBuilding())) {
            Building building = residentialComplex.getBuilding();
            this.buildingDto = new BuildingDto(building);
        }
    }
}
