package kz.dilau.htcdatamanager.web.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceDto {

    @ApiModelProperty(value = "Инфо о устройстве")
    private DeviceDto device;

    @ApiModelProperty(value = "логин клиента")
    private String login;
}
