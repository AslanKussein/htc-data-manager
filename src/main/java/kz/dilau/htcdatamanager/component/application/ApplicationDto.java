package kz.dilau.htcdatamanager.component.application;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "Модель заявки", description = "Модель заявки")
@Getter
@Setter
public class ApplicationDto {
    @ApiModelProperty(name = "id", value = "ID заявки")
    private Long id;
    @ApiModelProperty(name = "clientId", value = "ID клиента")
    private Long clientId;
    @ApiModelProperty(name = "firstName", value = "Имя")
    private String firstName;
    @ApiModelProperty(name = "surname", value = "Фамилия")
    private String surname;
    @ApiModelProperty(name = "patronymic", value = "Отчество")
    private String patronymic;
    @ApiModelProperty(name = "phoneNumber", value = "Контакты(номер телефона)")
    private String phoneNumber;
    @ApiModelProperty(name = "email", value = "Почта")
    private String email;
    @ApiModelProperty(name = "gender", value = "Пол", dataType = "string", allowableValues = "MALE, FEMALE, UNKNOWN")
    private Gender gender = Gender.UNKNOWN;
    @ApiModelProperty(name = "operationTypeId", value = "ID вида операции")
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(name = "objectTypeId", value = "ID типа объекта")
    private Long objectTypeId;
    @ApiModelProperty(name = "objectPrice", value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(name = "objectPriceFrom", value = "Цена объекта от(млн тг)")
    private BigDecimal objectPriceFrom;
    @ApiModelProperty(name = "objectPriceTo", value = "Цена объекта до(млн тг)")
    private BigDecimal objectPriceTo;
    @ApiModelProperty(name = "mortgage", value = "Ипотека")
    private Boolean mortgage;//ипотека
    @ApiModelProperty(name = "encumbrance", value = "Обременение")
    private Boolean encumbrance;//обременение
    @ApiModelProperty(name = "sharedOwnershipProperty", value = "Общая долевая собственность")
    private Boolean sharedOwnershipProperty;//общая долевая собственность
    @ApiModelProperty(name = "exchange", value = "Обмен")
    private Boolean exchange;//обмен
    @ApiModelProperty(name = "probabilityOfBidding", value = "Вероятность торга")
    private Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(name = "theSizeOfTrades", value = "Размер торга")
    private String theSizeOfTrades;//размер торга
    @ApiModelProperty(name = "possibleReasonForBiddingIdList", value = "ID возможных причин торга")
    private List<Long> possibleReasonForBiddingIdList;
    @ApiModelProperty(name = "contractPeriod", value = "Срок действия договора")
    private Date contractPeriod;
    @ApiModelProperty(name = "amount", value = "Сумма по договору")
    private BigDecimal amount;
    @ApiModelProperty(name = "isCommissionIncludedInThePrice", value = "Комиссия включена в стоимость")
    @NotNull(message = "Commission is included in the price must not be null")
    private boolean isCommissionIncludedInThePrice = false;
    @ApiModelProperty(name = "note", value = "Примечание")
    private String note;
    @ApiModelProperty(name = "cityId", value = "ID города")
    private Long cityId;
    @ApiModelProperty(name = "cadastralNumber", value = "Кадастровый номер")
    private String cadastralNumber;
    @ApiModelProperty(name = "residentialComplexId", value = "ID жилого комплекса")
    private Long residentialComplexId;
    @ApiModelProperty(name = "street", value = "Улица")
    private String street;
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
    private Double totalArea;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private Double livingArea;
    @ApiModelProperty(name = "kitchenArea", value = "Площадь кухни")
    private Double kitchenArea;
    @ApiModelProperty(name = "balconyArea", value = "Площадь балкона")
    private Double balconyArea;
    @ApiModelProperty(name = "ceilingHeight", value = "Высота потолков")
    private Double ceilingHeight;
    @ApiModelProperty(name = "numberOfBedrooms", value = "Количество спален")
    private Integer numberOfBedrooms;
    @ApiModelProperty(name = "atelier", value = "Студия")
    private Boolean atelier;
    @ApiModelProperty(name = "separateBathroom", value = "Санузел раздельный")
    private Boolean separateBathroom;
    @ApiModelProperty(name = "districtId", value = "ID района")
    private Long districtId;
    @ApiModelProperty(name = "district", value = "Район")
    private String district;
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;//residentialComplex
    @ApiModelProperty(name = "numberOfFloorsFrom", value = "Этажность дома от")
    private Integer numberOfFloorsFrom;
    @ApiModelProperty(name = "numberOfFloorsTo", value = "Этажность дома до")
    private Integer numberOfFloorsTo;
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
    @ApiModelProperty(name = "floorFrom", value = "Этаж от")
    private Integer floorFrom;
    @ApiModelProperty(name = "floorTo", value = "Этаж до")
    private Integer floorTo;
    @ApiModelProperty(name = "numberOfRoomsFrom", value = "Количество комнат от")
    private Integer numberOfRoomsFrom;
    @ApiModelProperty(name = "numberOfRoomsTo", value = "Количество комнат до")
    private Integer numberOfRoomsTo;
    @ApiModelProperty(name = "totalAreaFrom", value = "Общая площадь от")
    private Double totalAreaFrom;
    @ApiModelProperty(name = "totalAreaTo", value = "Общая площадь до")
    private Double totalAreaTo;
    @ApiModelProperty(name = "livingAreaFrom", value = "Жилая площадь от")
    private Double livingAreaFrom;
    @ApiModelProperty(name = "livingAreaTo", value = "Жилая площадь до")
    private Double livingAreaTo;
    @ApiModelProperty(name = "kitchenAreaFrom", value = "Площадь кухни от")
    private Double kitchenAreaFrom;
    @ApiModelProperty(name = "kitchenAreaTo", value = "Площадь кухни до")
    private Double kitchenAreaTo;
    @ApiModelProperty(name = "balconyAreaFrom", value = "Площадь балкона от")
    private Double balconyAreaFrom;
    @ApiModelProperty(name = "balconyAreaTo", value = "Площадь балкона до")
    private Double balconyAreaTo;
    @ApiModelProperty(name = "ceilingHeightFrom", value = "Высота потолков от")
    private Double ceilingHeightFrom;
    @ApiModelProperty(name = "ceilingHeightTo", value = "Высота потолков до")
    private Double ceilingHeightTo;
    @ApiModelProperty(name = "numberOfBedroomsFrom", value = "Количество спален от")
    private Integer numberOfBedroomsFrom;
    @ApiModelProperty(name = "numberOfBedroomsTo", value = "Количество спален до")
    private Integer numberOfBedroomsTo;
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
    private Double landArea;
    @ApiModelProperty(name = "landAreaFrom", value = "Площадь участка от")
    private Double landAreaFrom;
    @ApiModelProperty(name = "landAreaTo", value = "Площадь участка до")
    private Double landAreaTo;
}
