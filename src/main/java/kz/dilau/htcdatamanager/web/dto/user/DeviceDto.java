package kz.dilau.htcdatamanager.web.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    @ApiModelProperty(value = "UUID")
    private String uuid;

    @ApiModelProperty(value = "платформа")
    private String platform;

    @ApiModelProperty(value = "Версия ПО")
    private String version;
}
