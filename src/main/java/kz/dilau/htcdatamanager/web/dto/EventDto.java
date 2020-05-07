package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Event;
import lombok.*;

import java.util.Date;

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

    @ApiModelProperty(value = "Выбранная заявка")
    private Long targetApplicationId;

    @ApiModelProperty(value = "Дата и время события", required = true)
    private Date eventDate;

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
        this.sourceApplicationId = event.getSourceApplication().getId();
        if (nonNull(event.getTargetApplication())) {
            this.targetApplicationId = event.getTargetApplication().getId();
        }
        this.description = event.getDescription();
        this.comment = event.getComment();
    }
}
