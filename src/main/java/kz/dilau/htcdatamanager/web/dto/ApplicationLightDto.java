package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель краткой формы заявки")
public class ApplicationLightDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "Данные по клиенту", required = true)
    @NotNull(message = "Client must not be null")
    private ClientDto clientDto;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(value = "Примечание")
    private String note;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
}
