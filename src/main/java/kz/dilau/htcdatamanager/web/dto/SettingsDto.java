package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Settings;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SettingsDto", description = "Модель настроек")
public class SettingsDto {
    @NonNull
    @ApiModelProperty(value = "ключ", required = true)
    private String key;

    @NonNull
    @ApiModelProperty(value = "Значение", required = false)
    private String val;
}
