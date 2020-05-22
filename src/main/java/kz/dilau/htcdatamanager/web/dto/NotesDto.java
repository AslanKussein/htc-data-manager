package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Notes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@ApiModel(description = "Модель сущности Комментарий")
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {
    @ApiModelProperty(value = "ID Кооментария")
    private Long id;
    @ApiModelProperty(value = "Логин автора")
    private String createdBy;
    @ApiModelProperty(value = "Текст")
    private String text;
    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime crDate;
    @ApiModelProperty(value = "ID объекта")
    @NotNull
    private Long realPropertyId;

    public NotesDto(Notes notes) {
        this.id = notes.getId();
        this.createdBy = notes.getCreatedBy();
        this.text = notes.getText();
        this.crDate = ZonedDateTime.ofInstant(notes.getCreatedDate().toInstant(), ZoneId.systemDefault());
        this.realPropertyId = notes.getRealProperty().getId();
    }

}
