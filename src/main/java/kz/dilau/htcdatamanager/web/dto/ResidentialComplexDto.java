package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.old.OldGeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.dictionary.ParkingType;
import kz.dilau.htcdatamanager.domain.dictionary.OldResidentialComplex;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApiModel(description = "Модель для справочника Жилой комплекс")
@Getter
@Setter
@NoArgsConstructor
public class ResidentialComplexDto {
    @ApiModelProperty(value = "ID жилого комплекса")
    private Long id;
//    @ApiModelProperty(value = "ID страны")
//    private Long countryId;
    @ApiModelProperty(value = "ID города")
    private Long cityId;
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
    private BigDecimal ceilingHeight;
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
    private List<Long> parkingTypeIds;
    @ApiModelProperty(value = "ID типа двора")
    private Long yardTypeId;
    @ApiModelProperty(value = "Детская площадка")
    private Boolean playground;

    public ResidentialComplexDto(OldResidentialComplex rc) {
        this.id = rc.getId();
        this.houseName = rc.getHouseName();
        this.numberOfEntrances = rc.getNumberOfEntrances();
        if (Objects.nonNull(rc.getGeneralCharacteristics())) {
            OldGeneralCharacteristics gc = rc.getGeneralCharacteristics();
            this.apartmentsOnTheSite = gc.getApartmentsOnTheSite();
            this.ceilingHeight = gc.getCeilingHeight();
            this.concierge = gc.getConcierge();
            this.houseNumber = gc.getHouseNumber();
            this.houseNumberFraction = gc.getHouseNumberFraction();
            this.housingClass = gc.getHousingClass();
            this.housingCondition = gc.getHousingCondition();
            this.numberOfApartments = gc.getNumberOfApartments();
            this.numberOfFloors = gc.getNumberOfFloors();
            this.playground = gc.getPlayground();
            this.wheelchair = gc.getWheelchair();
            this.yearOfConstruction = gc.getYearOfConstruction();
            if (Objects.nonNull(gc.getYardType())) {
                this.yardTypeId = gc.getYardType().getId();
            }
            if (Objects.nonNull(gc.getCity())) {
                this.cityId = gc.getCity().getId();
            }
            if (Objects.nonNull(gc.getDistrict())) {
                this.districtId = gc.getDistrict().getId();
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
                        .map(TypeOfElevator::getId)
                        .collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(gc.getParkingTypes())) {
                this.parkingTypeIds = gc
                        .getParkingTypes()
                        .stream()
                        .map(ParkingType::getId)
                        .collect(Collectors.toList());
            }
            if (Objects.nonNull(gc.getStreet())) {
                this.streetId = gc.getStreet().getId();
            }
        }
    }
}
