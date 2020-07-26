package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.GeneralCharacteristics;
import kz.dilau.htcdatamanager.domain.IdItem;
import kz.dilau.htcdatamanager.domain.base.MultiLang;
import kz.dilau.htcdatamanager.domain.dictionary.HouseCondition;
import kz.dilau.htcdatamanager.service.dictionary.DictionaryDto;
import kz.dilau.htcdatamanager.web.dto.AGeneralCharacteristicsDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "GeneralCharacteristicsClientViewDto", description = "Характеристики недвижимости")
public class GeneralCharacteristicsClientViewDto extends AGeneralCharacteristicsDto {
    @ApiModelProperty(value = "вида паркинга (мультивыбор)")
    protected List<DictionaryDto> parkingTypeList;
    //    @ApiModelProperty(name = "residentialComplexId", value = "ID жилого комплекса")
//    private Long residentialComplexId;
    @ApiModelProperty(name = "propertyDeveloperId", value = "Застройщик")
    private Long propertyDeveloperId;
    @ApiModelProperty(name = "housingClass", value = "Класс жилья")
    private String housingClass;
    @ApiModelProperty(name = "houseClassId", value = "Класс жилья")
    private Long houseClassId;
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
    private DictionaryDto houseCondition;

    public GeneralCharacteristicsClientViewDto(GeneralCharacteristics generalCharacteristics) {
        if (nonNull(generalCharacteristics)) {
            this.propertyDeveloperId = generalCharacteristics.getPropertyDeveloperId();
            this.housingClass = generalCharacteristics.getHousingClass();
            this.houseClassId = generalCharacteristics.getHouseClassId();
            this.yearOfConstruction = generalCharacteristics.getYearOfConstruction();
            this.numberOfFloors = generalCharacteristics.getNumberOfFloors();
            this.numberOfApartments = generalCharacteristics.getNumberOfApartments();
            this.apartmentsOnTheSite = generalCharacteristics.getApartmentsOnTheSite();
            this.ceilingHeight = generalCharacteristics.getCeilingHeight();
            if (generalCharacteristics.getHouseCondition() != null) {
                HouseCondition hCondition = generalCharacteristics.getHouseCondition();
                this.houseCondition = fillDictionaryDto(hCondition.getId(), hCondition.getMultiLang());
            }
            this.materialOfConstructionId = generalCharacteristics.getMaterialOfConstructionId();
            this.concierge = generalCharacteristics.getConcierge();
            this.wheelchair = generalCharacteristics.getWheelchair();
            this.yardTypeId = generalCharacteristics.getYardTypeId();
            this.playground = generalCharacteristics.getPlayground();
            this.typeOfElevatorList = generalCharacteristics.getTypesOfElevator().stream().map(IdItem::getId).collect(Collectors.toList());
            this.parkingTypeIds = generalCharacteristics.getParkingTypes().stream().map(IdItem::getId).collect(Collectors.toList());
        }
    }


    private DictionaryDto fillDictionaryDto(Long id, MultiLang multiLang) {
        return DictionaryDto.builder()
                .id(id)
                .nameEn(multiLang.getNameEn())
                .nameKz(multiLang.getNameKz())
                .nameRu(multiLang.getNameRu())
                .build();
    }
}


