package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.old.OldEvent;
import lombok.*;

import java.util.Date;

import static java.util.Objects.nonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ViewEventDto", description = "Модель просмотра события")
public class ViewEventDto {
    @ApiModelProperty(value = "ID события")
    private Long id;

    @ApiModelProperty(value = "Выбранная заявка")
    private Long targetApplicationId;

    @ApiModelProperty(value = "Дата и время события")
    private Date eventDate;

    @ApiModelProperty(value = "Описание события")
    private String description;

    @ApiModelProperty(value = "ID Вида события")
    private Long eventTypeId;

    @ApiModelProperty(value = "Комментарий (Результат события)")
    private String comment;

    @ApiModelProperty(value = "Фото объекта")
    private String photo;

    @ApiModelProperty(value = "ID объекта")
    private Long realPropertyId;

    public ViewEventDto(OldEvent event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.eventTypeId = event.getEventType().getId();
        if (nonNull(event.getTargetApplication())) {
            this.targetApplicationId = event.getTargetApplication().getId();
        }
        this.description = event.getDescription();
        this.comment = event.getComment();
    }
}
