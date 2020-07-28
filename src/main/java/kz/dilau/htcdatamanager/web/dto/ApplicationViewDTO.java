package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Модель заявки для просмотра", description = "Модель заявки для просмотра")
public class ApplicationViewDTO {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(name = "clientLogin", value = "Логин Клиента")
    private String clientLogin;
    @ApiModelProperty(name = "agent", value = "Агент")
    private String agent;
    @ApiModelProperty(name = "operationType", value = "операции")
    private MultiLangText operationType;
    @ApiModelProperty(value = "Это продать")
    private Boolean isSell;
    @ApiModelProperty(value = "Это квартира")
    private Boolean isFlat;
    @ApiModelProperty(name = "objectType", value = "объект")
    private MultiLangText objectType;
    @ApiModelProperty(name = "objectPrice", value = "цена объекта")
    private BigDecimal objectPrice;
    @ApiModelProperty(name = "objectPricePeriod", value = "цена объекта период")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "ID признаков заявки")
    private List<MultiLangText> applicationFlagIdList;
    @ApiModelProperty(value = "Вероятность торга")
    private Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(value = "ID возможных причин торга")
    private List<MultiLangText> possibleReasonForBiddingIdList;
    @ApiModelProperty(value = "Размер торга")
    private Integer theSizeOfTrades;//размер торга
    @ApiModelProperty(value = "Комментарий")
    private String comment;
    @ApiModelProperty(value = "Описание")
    private String description;
    @ApiModelProperty(value = "Количество комнат от и до")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(name = "numberOfRooms", value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(name = "floorPeriod", value = "Этаж от и до")
    private IntegerPeriod floorPeriod;
    @ApiModelProperty(name = "landAreaPeriod", value = "Участок от и до")
    private BigDecimalPeriod landAreaPeriod;
    @ApiModelProperty(name = "floor", value = "Этаж")
    private Integer floor;
    @ApiModelProperty(name = "floor", value = "участок")
    private BigDecimal landArea;
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
    @ApiModelProperty(name = "districts", value = "Районы")
    private List<MultiLangText> districts;
    @ApiModelProperty(name = "street", value = "Улица")
    private MultiLangText street;
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Количество квартир на площадке")
    private Integer apartmentsOnTheSite;
    @ApiModelProperty(name = "materialOfConstruction", value = "Материал постройки")
    private MultiLangText materialOfConstruction;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;
    @ApiModelProperty(name = "yearOfConstructionPeriod", value = "Год постройки")
    private IntegerPeriod yearOfConstructionPeriod;
    @ApiModelProperty(name = "apartmentsOnTheSitePeriod", value = "Год постройки")
    private IntegerPeriod apartmentsOnTheSitePeriod;
    @ApiModelProperty(name = "typeOfElevatorList", value = "Тип лифта(мультивыбор)")
    private List<MultiLangText> typeOfElevatorList;
    @ApiModelProperty(name = "concierge", value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(name = "wheelchair", value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(name = "yardType", value = "Двор(закрытый/открытый)", dataType = "string", allowableValues = "PRIVATE, OPEN")
    private MultiLangText yardType;
    @ApiModelProperty(name = "playground", value = "Детская площадка")
    private Boolean playground;
    @ApiModelProperty(value = "типа парковки")
    private List<MultiLangText> parkingTypes;
    @ApiModelProperty(value = "Состояние")
    private MultiLangText houseCondition;
    @ApiModelProperty(value = "Канализация")
    private MultiLangText sewerage;
    @ApiModelProperty(value = "Отопление")
    private MultiLangText heatingSystem;
    @ApiModelProperty(name = "mortgage", value = "ипотека")
    private Boolean mortgage;
    @ApiModelProperty(name = "encumbrance", value = "Обременение")
    private Boolean encumbrance;
    @ApiModelProperty(name = "sharedOwnershipProperty", value = "Общая долевая собственность")
    private Boolean sharedOwnershipProperty;
    @ApiModelProperty(name = "exchange", value = "Обмен")
    private Boolean exchange;
    @ApiModelProperty(name = "city", value = "Город", dataType = "string")
    private MultiLangText city;
    @ApiModelProperty(name = "fullAddress", value = "Почта", dataType = "string")
    private MultiLangText fullAddress;
    @ApiModelProperty(name = "latitude", value = "Почта", dataType = "BigDecimal")
    private BigDecimal latitude;
    @ApiModelProperty(name = "longitude", value = "Почта", dataType = "BigDecimal")
    private BigDecimal longitude;
    @ApiModelProperty(name = "residenceComplex", value = "ЖК", dataType = "string")
    private String residenceComplex;
    @ApiModelProperty(name = "apartmentNumber", value = "Номер квартиры", dataType = "string")
    private String apartmentNumber;
    @ApiModelProperty(name = "houseNumber", value = "Номер дома", dataType = "string")
    private String houseNumber;
    @ApiModelProperty(name = "photoIdList", value = "Список ID фотографии")
    private Set<String> photoIdList;
    @ApiModelProperty(name = "housingPlanImageIdList", value = "Список ID фотографии")
    private Set<String> housingPlanImageIdList;
    @ApiModelProperty(name = "virtualTourImageIdList", value = "Список ID фотографии")
    private Set<String> virtualTourImageIdList;
    @ApiModelProperty(value = "Список доступных операций по текущей заявке")
    private List<String> operationList;
    @ApiModelProperty(value = "Код почты")
    private String postcode;
}
