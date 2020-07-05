package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Модель заявки для просмотра из кп", description = "Модель заявки для просмотра из КП")
public class ApplicationViewClientDTO {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(name = "operationType", value = "операции")
    private MultiLang operationType;
    @ApiModelProperty(value = "Это продать")
    private Boolean isSell;
    @ApiModelProperty(value = "Это квартира")
    private Boolean isFlat;
    @ApiModelProperty(name = "objectType", value = "объект")
    private MultiLang objectType;
    @ApiModelProperty(name = "objectPrice", value = "цена объекта")
    private BigDecimal objectPrice;
    @ApiModelProperty(name = "objectPricePeriod", value = "цена объекта период")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "Вероятность торга")
    private Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(value = "Комментарий")
    private String comment;
    @ApiModelProperty(value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(name = "numberOfRooms", value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "floorPeriod", value = "Этаж от и до")
    private IntegerPeriod floorPeriod;
    @ApiModelProperty(name = "floor", value = "Этаж")
    private Integer floor;
    @ApiModelProperty(name = "totalAreaPeriod", value = "Общая площадь от и до")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(name = "numberOfFloorsPeriod", value = "Общая этажность от и до")
    private IntegerPeriod numberOfFloorsPeriod;
    @ApiModelProperty(name = "totalArea", value = "Общая площадь")
    private BigDecimal totalArea;
    @ApiModelProperty(value = "Жилая площадь от и до")
    private BigDecimalPeriod livingAreaPeriod;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private BigDecimal livingArea;
    @ApiModelProperty(value = "Площадь кухни от и до")
    private BigDecimalPeriod kitchenAreaPeriod;
    @ApiModelProperty(name = "kitchenArea", value = "Жилая кухни")
    private BigDecimal kitchenArea;
    @ApiModelProperty(value = "Площадь балкона от и до")
    private BigDecimalPeriod balconyAreaPeriod;
    @ApiModelProperty(name = "balconyArea", value = "Площадь балкона")
    private BigDecimal balconyArea;
    @ApiModelProperty(value = "Высота потолков от и до")
    private BigDecimalPeriod ceilingHeightPeriod;
    @ApiModelProperty(name = "ceilingHeight", value = "Высота потолков")
    private BigDecimal ceilingHeight;
    @ApiModelProperty(value = "Количество спален от и до")
    private IntegerPeriod numberOfBedroomsPeriod;
    @ApiModelProperty(name = "numberOfBedrooms", value = "Количество спален")
    private Integer numberOfBedrooms;
    @ApiModelProperty(name = "atelier", value = "Студия")
    private Boolean atelier;
    @ApiModelProperty(name = "separateBathroom", value = "Санузел раздельный")
    private Boolean separateBathroom;
    @ApiModelProperty(name = "district", value = "Район")
    private MultiLang district;
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Количество квартир на площадке")
    private Integer apartmentsOnTheSite;
    @ApiModelProperty(name = "materialOfConstruction", value = "Материал постройки")
    private MultiLang materialOfConstruction;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;
    @ApiModelProperty(name = "yearOfConstructionPeriod", value = "Год постройки")
    private IntegerPeriod yearOfConstructionPeriod;
    @ApiModelProperty(name = "typeOfElevatorList", value = "Тип лифта(мультивыбор)")
    private List<MultiLang> typeOfElevatorList;
    @ApiModelProperty(name = "concierge", value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(name = "wheelchair", value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(name = "yardType", value = "Двор(закрытый/открытый)", dataType = "string", allowableValues = "PRIVATE, OPEN")
    private MultiLang yardType;
    @ApiModelProperty(name = "playground", value = "Детская площадка")
    private Boolean playground;
    @ApiModelProperty(value = "типа парковки")
    private List<MultiLang> parkingTypes;
    @ApiModelProperty(value = "Состояние")
    private MultiLang houseCondition;
    @ApiModelProperty(name = "mortgage", value = "ипотека")
    private Boolean mortgage;
    @ApiModelProperty(name = "encumbrance", value = "Обременение")
    private Boolean encumbrance;
    @ApiModelProperty(name = "sharedOwnershipProperty", value = "Общая долевая собственность")
    private Boolean sharedOwnershipProperty;
    @ApiModelProperty(name = "exchange", value = "Обмен")
    private Boolean exchange;
    @ApiModelProperty(name = "fullAddress", value = "Почта", dataType = "string")
    private MultiLangText fullAddress;
    @ApiModelProperty(name = "residenceComplex", value = "ЖК", dataType = "string")
    private String residenceComplex;
    @ApiModelProperty(name = "apartmentNumber", value = "Номер квартиры", dataType = "string")
    private String apartmentNumber;
    @ApiModelProperty(name = "photoIdList", value = "Список ID фотографии")
    private Set<String> photoIdList;
    @ApiModelProperty(name = "housingPlanImageIdList", value = "Список ID фотографии")
    private Set<String> housingPlanImageIdList;
    @ApiModelProperty(name = "virtualTourImageIdList", value = "Список ID фотографии")
    private Set<String> virtualTourImageIdList;
}
