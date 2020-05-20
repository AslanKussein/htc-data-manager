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
@ApiModel(value = "ApplicationLightDto", description = "Модель краткой формы заявки")
public class ApplicationLightDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(name = "clientLogin", value = "Логин Клиента")
    @NotNull
    private String clientLogin;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(value = "ID вида недвижимости", required = true)
    @NotNull(message = "Object type must not be null")
    private Long objectTypeId;
    @ApiModelProperty(value = "Примечание")
    private String note;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
}
