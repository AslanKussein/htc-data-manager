package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.BigDecimalPeriod;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import kz.dilau.htcdatamanager.web.dto.common.IntegerPeriod;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CompleteTargetApplicationDto", description = "Модель связанной заявки для заврешения сделки")
public class CompleteTargetApplicationDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "Вид операции")
    private DictionaryMultilangItemDto operationType;
    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime createDate;
    @ApiModelProperty(value = "Район")
    private DictionaryMultilangItemDto district;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agentLogin;
    @ApiModelProperty(value = "ФИО агента, на кого назначена заявка")
    private String agentFullname;
    @ApiModelProperty(value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(value = "Количество комнат")
    private Integer numberOfRooms;
    @ApiModelProperty(value = "Площадь")
    private BigDecimal totalArea;
    @ApiModelProperty(value = "Этаж")
    private Integer floor;
    @ApiModelProperty(value = "Статус заявки")
    private DictionaryMultilangItemDto status;
    @ApiModelProperty(value = "Цена объекта(млн тг) ОТ и ДО")
    private BigDecimalPeriod objectPricePeriod;
    @ApiModelProperty(value = "Количество комнат ОТ и ДО")
    private IntegerPeriod numberOfRoomsPeriod;
    @ApiModelProperty(value = "Площадь ОТ и ДО")
    private BigDecimalPeriod totalAreaPeriod;
    @ApiModelProperty(value = "Этаж ОТ и ДО")
    private IntegerPeriod floorPeriod;

}
