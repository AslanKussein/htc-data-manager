package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "EventDto", description = "Модель создания события")
public class EventDto {
    @ApiModelProperty(value = "ID события")
    protected Long id;

    @ApiModelProperty(value = "ID заявки инициатора", required = true)
    protected Long sourceApplicationId;

    @ApiModelProperty(value = "Признак брони недвижимости в заявке инициатора")
    protected Boolean isSourceReserved = false;

    @ApiModelProperty(value = "Выбранная заявка")
    protected Long targetApplicationId;

    @ApiModelProperty(value = "Признак брони недвижимости в выбранной заявке")
    protected Boolean isTargetReserved = false;

    @ApiModelProperty(value = "Дата и время события", required = true)
    protected ZonedDateTime eventDate;

    @ApiModelProperty(value = "Описание события")
    protected String description;

    @ApiModelProperty(value = "ID Вида события", required = true)
    protected Long eventTypeId;

    @ApiModelProperty(value = "Комментарий (Результат события)")
    protected String comment;

}
