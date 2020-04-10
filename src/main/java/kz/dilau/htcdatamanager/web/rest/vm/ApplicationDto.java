package kz.dilau.htcdatamanager.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import kz.dilau.htcdatamanager.domain.enums.YardType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private Long operationTypeId;
    @ApiModelProperty(name = "objectTypeId", value = "ID типа объекта")
    private Long objectTypeId;
    @ApiModelProperty(name = "objectPrice", value = "Цена объекта(млн тг)")
    private Double objectPrice;
    @ApiModelProperty(name = "objectPriceFrom", value = "Цена объекта от(млн тг)")
    private Double objectPriceFrom;
    @ApiModelProperty(name = "objectPriceTo", value = "Цена объекта до(млн тг)")
    private Double objectPriceTo;
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
    @ApiModelProperty(name = "possibleReasonForBiddingId", value = "ID возможной причины торга")
    private Long possibleReasonForBiddingId;
    @ApiModelProperty(name = "contractPeriod", value = "Срок действия договора")
    private Date contractPeriod;
    @ApiModelProperty(name = "amount", value = "Сумма по договору")
    private Integer amount;
    @ApiModelProperty(name = "isCommissionIncludedInThePrice", value = "Комиссия включена в стоимость")
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
    private Integer totalArea;
    @ApiModelProperty(name = "livingArea", value = "Жилая площадь")
    private Integer livingArea;
    @ApiModelProperty(name = "kitchenArea", value = "Площадь кухни")
    private Integer kitchenArea;
    @ApiModelProperty(name = "balconyArea", value = "Площадь балкона")
    private Integer balconyArea;
    @ApiModelProperty(name = "ceilingHeight", value = "Высота потолков")
    private Integer ceilingHeight;
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
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Квартир на площадке")
    private String apartmentsOnTheSite;//residentialComplex
    @ApiModelProperty(name = "materialOfConstruction", value = "Материал постройки")
    private String materialOfConstruction;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;//residentialComplex
    @ApiModelProperty(name = "typeOfElevator", value = "Тип лифта")
    private String typeOfElevator;
    @ApiModelProperty(name = "concierge", value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(name = "wheelchair", value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(name = "yardType", value = "Двор(закрытый/открытый)", dataType = "string", allowableValues = "PRIVATE, OPEN")
    private YardType yardType;
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
    private Integer totalAreaFrom;
    @ApiModelProperty(name = "totalAreaTo", value = "Общая площадь до")
    private Integer totalAreaTo;
    @ApiModelProperty(name = "livingAreaFrom", value = "Жилая площадь от")
    private Integer livingAreaFrom;
    @ApiModelProperty(name = "livingAreaTo", value = "Жилая площадь до")
    private Integer livingAreaTo;
    @ApiModelProperty(name = "kitchenAreaFrom", value = "Площадь кухни от")
    private Integer kitchenAreaFrom;
    @ApiModelProperty(name = "kitchenAreaTo", value = "Площадь кухни до")
    private Integer kitchenAreaTo;
    @ApiModelProperty(name = "balconyAreaFrom", value = "Площадь балкона от")
    private Integer balconyAreaFrom;
    @ApiModelProperty(name = "balconyAreaTo", value = "Площадь балкона до")
    private Integer balconyAreaTo;
    @ApiModelProperty(name = "ceilingHeightFrom", value = "Высота потолков от")
    private Integer ceilingHeightFrom;
    @ApiModelProperty(name = "ceilingHeightTo", value = "Высота потолков до")
    private Integer ceilingHeightTo;
    @ApiModelProperty(name = "numberOfBedroomsFrom", value = "Количество спален от")
    private Integer numberOfBedroomsFrom;
    @ApiModelProperty(name = "numberOfBedroomsTo", value = "Количество спален до")
    private Integer numberOfBedroomsTo;
}
