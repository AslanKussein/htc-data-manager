package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Application;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Модель заявки для КП", description = "Модель заявки для КП")
public class ApplicationClientDTO {
    protected ZonedDateTime createdDate;
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(value = "ID типа объекта", required = true)
    private Long objectTypeId;
    @ApiModelProperty(name = "clientLogin", value = "Логин Клиента")
    private String clientLogin;
    @ApiModelProperty(name = "deviceUuid", value = "Устройство с которого сохранили")
    private String deviceUuid;
    @ApiModelProperty(name = "sellDataClientDto", value = "Общая информация о сделке продажи объекта")
    private ApplicationSellDataClientDto sellDataClientDto;
    @ApiModelProperty(name = "purchaseInfoClientDto", value = "Модель параметров по операции Покупка ")
    private PurchaseInfoClientDto purchaseInfoClientDto;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;

    public ApplicationClientDTO(Application application) {
        this.setId(application.getId());
        this.setAgent(application.getCurrentAgent());
        this.setClientLogin(application.getClientLogin());
        this.setOperationTypeId(application.getOperationTypeId());
        this.setObjectTypeId(application.getObjectTypeId());
        this.setCreatedDate(application.getCreatedDate());
        if (application.getOperationType().isSell() && nonNull(application.getApplicationSellData())) {
            this.setSellDataClientDto(new ApplicationSellDataClientDto(application.getApplicationSellData()));
        } else if (application.getOperationType().isBuy() && nonNull(application.getApplicationPurchaseData())
                && nonNull(application.getApplicationPurchaseData().getPurchaseInfo())) {
            this.setPurchaseInfoClientDto(new PurchaseInfoClientDto(application.getApplicationPurchaseData()));
        }
    }
}
