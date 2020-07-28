package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDeviceDto {

    @ApiModelProperty(value = "ID устройства")
    private String deviceUuid;

    @ApiModelProperty(value = "логин клиента")
    private String clientLogin;

    @ApiModelProperty(value = "платформа")
    private String platform;

    @ApiModelProperty(value = "Версия ПО")
    private String version;
}
