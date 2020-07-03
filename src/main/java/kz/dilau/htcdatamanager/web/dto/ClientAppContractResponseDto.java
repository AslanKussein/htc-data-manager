package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClientAppContractResponseDto", description = "Модель ответа на формирования договора КП")
public class ClientAppContractResponseDto {
    @ApiModelProperty(value = "Тип ответа guid или base64" , required = true)
    private String sourceType;

    @ApiModelProperty(value = "Сам ответ" , required = true)
    private String sourceStr;
}
