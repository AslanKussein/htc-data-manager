package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.ApplicationPurchaseDataDto;
import kz.dilau.htcdatamanager.web.dto.ApplicationSellDataDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Модель заявки для КП", description = "Модель заявки для КП")
public class ApplicationClientDTO {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;

    @ApiModelProperty(value = "ID типа объекта", required = true)
    private Long objectTypeId;

    @ApiModelProperty(value = "Данные по невижимости", required = true)
    @NotNull(message = "Real property must not be null")
    private RealPropertyClientDto realPropertyDto;

    @ApiModelProperty(name = "clientLogin", value = "Логин Клиента")
    private String clientLogin;

    @ApiModelProperty(name = "separateBathroom", value = "Санузел раздельный")
    private Boolean mortgage;

    @ApiModelProperty(name = "sellDataDto", value = "Общая информация о сделке продажи объекта")
    private ApplicationSellDataDto sellDataDto;

    @ApiModelProperty(name = "purchaseInfoClientDto", value = "Модель параметров по операции Покупка ")
    private PurchaseInfoClientDto purchaseInfoDto;

    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
}
