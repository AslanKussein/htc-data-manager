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
    private Long id;

    @ApiModelProperty(value = "ID заявки инициатора", required = true)
    private Long sourceApplicationId;

    @ApiModelProperty(value = "Признак брони недвижимости в заявке инициатора")
    private Boolean isSourceReserved = false;

    @ApiModelProperty(value = "Выбранная заявка")
    private Long targetApplicationId;

    @ApiModelProperty(value = "Признак брони недвижимости в выбранной заявке")
    private Boolean isTargetReserved = false;

    @ApiModelProperty(value = "Дата и время события", required = true)
    private ZonedDateTime eventDate;

    @ApiModelProperty(value = "Описание события")
    private String description;

    @ApiModelProperty(value = "ID Вида события", required = true)
    private Long eventTypeId;

    @ApiModelProperty(value = "Комментарий (Результат события)")
    private String comment;

    public EventDto(Event event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.eventTypeId = event.getEventType().getId();
        if (nonNull(event.getSourceApplication())) {
            this.sourceApplicationId = event.getSourceApplication().getId();
            this.isSourceReserved = event.getSourceApplication().isReservedRealProperty();
        }
        if (nonNull(event.getTargetApplication())) {
            this.targetApplicationId = event.getTargetApplication().getId();
            this.isTargetReserved = event.getTargetApplication().isReservedRealProperty();
        }
        this.description = event.getDescription();
        this.comment = event.getComment();
    }
}
