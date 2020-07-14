package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import kz.dilau.htcdatamanager.web.dto.common.MultiLangText;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CompleteApplicationDto", description = "Модель заявки для заврешения сделки")
public class CompleteApplicationDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "ID вида операции")
    private DictionaryMultilangItemDto operationType;
    @ApiModelProperty(value = "ID типа объекта")
    private DictionaryMultilangItemDto objectType;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agentLogin;
    @ApiModelProperty(value = "ID агента, на кого назначена заявка")
    private Long agentId;
    @ApiModelProperty(value = "ФИО агента, на кого назначена заявка")
    private String agentFullname;
    @ApiModelProperty(value = "Логин Клиента")
    private String clientLogin;
    @ApiModelProperty(value = "ID Клиента")
    private Long clientId;
    @ApiModelProperty(value = "ФИО Клиента")
    private String clientFullname;
    @ApiModelProperty(value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(value = "Цена объекта(млн тг) ОТ и ДО")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "Адрес")
    private MultiLangText address;
    @ApiModelProperty(value = "Список ID фотографии")
    private Set<String> photoIdList;
    @ApiModelProperty(value = "Список ID фотографии")
    private Set<String> housingPlanImageIdList;
    @ApiModelProperty(value = "Список ID фотографии")
    private Set<String> virtualTourImageIdList;
    @ApiModelProperty(value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(value = "Количество комнат ОТ и ДО")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(value = "Статус заявки")
    private DictionaryMultilangItemDto status;
    @ApiModelProperty(value = "Обоснование")
    private String comment;
    @ApiModelProperty(value = "УРЛ договора")
    private String contractGuid;
    @ApiModelProperty(value = "УРЛ договора аванса/задатка")
    private String depositGuid;
    @ApiModelProperty(value = "Комиссия")
    private BigDecimal commission;
    @ApiModelProperty(value = "Сумма аванса/задатка")
    private BigDecimal depositSum;

    public Set<String> getPhotoIdList() {
        if (isNull(photoIdList)) {
            photoIdList = new HashSet<>();
        }
        return photoIdList;
    }

    public Set<String> getHousingPlanImageIdList() {
        if (isNull(housingPlanImageIdList)) {
            housingPlanImageIdList = new HashSet<>();
        }
        return housingPlanImageIdList;
    }

    public Set<String> getVirtualTourImageIdList() {
        if (isNull(virtualTourImageIdList)) {
            virtualTourImageIdList = new HashSet<>();
        }
        return virtualTourImageIdList;
    }
}
