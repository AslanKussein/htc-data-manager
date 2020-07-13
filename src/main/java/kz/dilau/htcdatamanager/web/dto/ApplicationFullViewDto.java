package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.client.RealPropertyClientViewDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationFullViewDto", description = "Модель заявки")
public class ApplicationFullViewDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(value = "ID типа объекта", required = true)
    private Long objectTypeId;

    private Long applicationStatusId;

    @ApiModelProperty(value = "Общая информация о сделке продажи объекта")
    private ApplicationSellDataDto sellDataDto;

    @ApiModelProperty(value = "Общая информация о сделке покупки объекта")
    private ApplicationPurchaseDataDto purchaseDataDto;

    @ApiModelProperty(value = "Идентификационные данные сделки")
    private ContractFormFullDto contractDto;

    @ApiModelProperty(value = "Общая информация об объекте продажи")
    private RealPropertyDto realPropertyDto;

    @ApiModelProperty(value = "Общая информация об объекте покупки")
    private PurchaseInfoDto purchaseInfoDto;

    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
    @ApiModelProperty(value = "Логин Клиента")
    private String clientLogin;

    @ApiModelProperty(value = "Признак бронирования")
    private Boolean isReserved;
}
