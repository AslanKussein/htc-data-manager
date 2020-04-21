package kz.dilau.htcdatamanager.component.property;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель сущности недвижимости")
public class RealPropertyRequestDto {
    @ApiModelProperty(name = "objectTypeId", value = "ID типа объекта")
    private Long objectTypeId;
    @ApiModelProperty(name = "cityId", value = "ID города")
    private Long cityId;
    @ApiModelProperty(name = "cadastralNumber", value = "Кадастровый номер")
    private String cadastralNumber;
    @ApiModelProperty(name = "residentialComplexId", value = "ID жилого комплекса")
    private Long residentialComplexId;
    @ApiModelProperty(name = "streetId", value = "ID улицы")
    private Long streetId;
    @ApiModelProperty(name = "houseNumber", value = "Номер дома")
    private Integer houseNumber;
    @ApiModelProperty(name = "houseNumberFraction(fraction/letter/building)", value = "Номер дома(дробь/буква/строение)")
    private String houseNumberFraction;
    @ApiModelProperty(name = "floor", value = "Этаж")
    private Integer floor;
    @ApiModelProperty(name = "apartmentNumber", value = "Номер квартиры(/буква)")
    private String apartmentNumber;
    @ApiModelProperty(name = "numberOfRooms", value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "totalArea", value = "Общая площадь")
    private BigDecimal totalArea;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private BigDecimal livingArea;
    @ApiModelProperty(name = "kitchenArea", value = "Площадь кухни")
    private BigDecimal kitchenArea;
    @ApiModelProperty(name = "balconyArea", value = "Площадь балкона")
    private BigDecimal balconyArea;
    @ApiModelProperty(name = "ceilingHeight", value = "Высота потолков")
    private BigDecimal ceilingHeight;
    @ApiModelProperty(name = "numberOfBedrooms", value = "Количество спален")
    private Integer numberOfBedrooms;
    @ApiModelProperty(name = "atelier", value = "Студия")
    private Boolean atelier;
    @ApiModelProperty(name = "separateBathroom", value = "Санузел раздельный")
    private Boolean separateBathroom;
    @ApiModelProperty(name = "districtId", value = "ID района")
    private Long districtId;
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;//residentialComplex
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Количество квартир на площадке")
    private String apartmentsOnTheSite;//residentialComplex
    @ApiModelProperty(name = "materialOfConstruction", value = "Материал постройки")
    private Long materialOfConstructionId;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;//residentialComplex
    @ApiModelProperty(name = "typeOfElevatorList", value = "Тип лифта(мультивыбор)")
    private List<Long> typeOfElevatorList;
    @ApiModelProperty(name = "concierge", value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(name = "wheelchair", value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(name = "yardType", value = "Двор(закрытый/открытый)", dataType = "string", allowableValues = "PRIVATE, OPEN")
    private Long yardTypeId;
    @ApiModelProperty(name = "playground", value = "Детская площадка")
    private Boolean playground;
    @ApiModelProperty(name = "parkingTypeId", value = "ID вида паркинга")
    private Long parkingTypeId;
    @ApiModelProperty(name = "propertyDeveloperId", value = "Застройщик")
    private Long propertyDeveloperId;
    @ApiModelProperty(name = "housingClass", value = "Класс жилья")
    private String housingClass;
    @ApiModelProperty(name = "housingCondition", value = "Состояние жилья")
    private String housingCondition;
    @ApiModelProperty(name = "photoIdList", value = "Список ID фотографии")
    private List<String> photoIdList;
    @ApiModelProperty(name = "housingPlanImageIdList", value = "Список ID фотографии")
    private List<String> housingPlanImageIdList;
    @ApiModelProperty(name = "virtualTourImageIdList", value = "Список ID фотографии")
    private List<String> virtualTourImageIdList;
    @ApiModelProperty(name = "sewerageId", value = "Канализация")
    private Long sewerageId;
    @ApiModelProperty(name = "heatingSystemId", value = "Отопительная система")
    private Long heatingSystemId;
    @ApiModelProperty(name = "numberOfApartments", value = "Общее количество квартир")
    private Integer numberOfApartments;
    @ApiModelProperty(name = "landArea", value = "Площадь участка")
    private BigDecimal landArea;
    @ApiModelProperty(value = "Параметры при Покупке")
    private PurchaseInfoDto purchaseInfoDto;
}


