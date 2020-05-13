package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.web.dto.PurchaseInfoDto;
import kz.dilau.htcdatamanager.web.dto.ResidentialComplexDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Модель сущности недвижимости для клиентского приложения")
public class RealPropertyClientViewDto  {
    @ApiModelProperty(name = "objectType", value = "Тип объекта")
    private DictionaryDto objectType;
    @ApiModelProperty(name = "city", value = "город")
    private DictionaryDto city;
    @ApiModelProperty(name = "cadastralNumber", value = "Кадастровый номер")
    private String cadastralNumber;
    @ApiModelProperty(name = "residentialComplex", value = "Жилой комплекс")
    private ResidentialComplexDto residentialComplex;
    @ApiModelProperty(name = "street", value = "Улица")
    private DictionaryDto street;
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
    @ApiModelProperty(name = "district", value = "Район")
    private DictionaryDto district;
    @ApiModelProperty(name = "numberOfFloors", value = "Этажность дома")
    private Integer numberOfFloors;//residentialComplex
    @ApiModelProperty(name = "apartmentsOnTheSite", value = "Количество квартир на площадке")
    private String apartmentsOnTheSite;//residentialComplex
    @ApiModelProperty(name = "materialOfConstruction", value = "Материал постройки")
    private DictionaryDto materialOfConstruction;
    @ApiModelProperty(name = "yearOfConstruction", value = "Год постройки")
    private Integer yearOfConstruction;//residentialComplex
    @ApiModelProperty(name = "typeOfElevatorList", value = "Тип лифта(мультивыбор)")
    private List<Long> typeOfElevatorList;
    @ApiModelProperty(name = "concierge", value = "Консьерж")
    private Boolean concierge;
    @ApiModelProperty(name = "wheelchair", value = "Колясочная")
    private Boolean wheelchair;
    @ApiModelProperty(name = "yardType", value = "Двор(закрытый/открытый)", dataType = "string", allowableValues = "PRIVATE, OPEN")
    private DictionaryDto yardType;
    @ApiModelProperty(name = "playground", value = "Детская площадка")
    private Boolean playground;
    @ApiModelProperty(name = "parkingTypeList", value = "вида паркинга (мультивыбор)")
    private Set<DictionaryDto> parkingTypeList;
    @ApiModelProperty(name = "propertyDeveloper", value = "Застройщик")
    private DictionaryDto propertyDeveloper;
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
    @ApiModelProperty(name = "sewerage", value = "Канализация")
    private DictionaryDto sewerage;
    @ApiModelProperty(name = "heatingSystem", value = "Отопительная система")
    private DictionaryDto heatingSystem;
    @ApiModelProperty(name = "numberOfApartments", value = "Общее количество квартир")
    private Integer numberOfApartments;
    @ApiModelProperty(name = "landArea", value = "Площадь участка")
    private BigDecimal landArea;
    @ApiModelProperty(value = "Параметры при Покупке")
    private PurchaseInfoDto purchaseInfoDto;
    @ApiModelProperty(value = "Координаты широты")
    private Double latitude;
    @ApiModelProperty(value = "Координаты долготы")
    private Double longitude;
}


