package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClientAppContractRequestDto", description = "Модель для формирования договора КП")
public class ClientAppContractRequestDto extends DepositFormDto {
    @ApiModelProperty(value = "ID заявки на продажу" , required = true)
    private Long sellApplicationId;
    @ApiModelProperty(value = "Сохранить или нет", required = true)
    private Boolean toSave;

    @NonNull
    @ApiModelProperty(value = "Тип оплаты (2 - Оплата 3 прц, 1 - Бронирование)", required = true)
    private Long payTypeId;
}
