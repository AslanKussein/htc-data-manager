package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClientAppBuyDto", description = "Модель для формирования договора КП")
public class ClientAppBuyDto {
    @ApiModelProperty(value = "ID заявки пользователя с типом купить")
    private Long currentApplicationId;

    @ApiModelProperty(value = "ID заявки с типом продать")
    private Long targetApplicationId;
}
