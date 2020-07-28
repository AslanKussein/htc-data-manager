package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.ProfileClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Запрос на создание заявки КП")
public class ClientApplicationCreateDTO {
    @ApiModelProperty("ID устройства")
    private String deviceUuid;

    @ApiModelProperty("тело заявки")
    private ApplicationClientDTO application;

    @ApiModelProperty("Имя клиента")
    private String clientName;

    @ApiModelProperty("Номер телефона клиента")
    private String phoneNumber;
}
