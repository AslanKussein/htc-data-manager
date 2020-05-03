package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.domain.Notes;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сущности Комментарий")
public class NotesDto {
    @ApiModelProperty(value = "ID Кооментария")
    private Long id;
    @ApiModelProperty(value = "Логин автора")
    private String login;
    @ApiModelProperty(value = "Текст")
    private String text;
    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime crDate;
    @ApiModelProperty(value = "ID объекта")
    @NotNull
    private Long realPropertyId;

    public NotesDto(Notes notes) {
        this.id = notes.getId();
        this.login = notes.getLogin();
        this.text = notes.getText();
        this.crDate = notes.getCreatedDate();
        this.realPropertyId = notes.getRealProperty().getId();
    }

}
