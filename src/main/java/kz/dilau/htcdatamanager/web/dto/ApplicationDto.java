package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.util.DictionaryMappingTool;
import kz.dilau.htcdatamanager.web.dto.client.PurchaseInfoClientDto;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationDto", description = "Модель заявки")
public class ApplicationDto {
    @ApiModelProperty(value = "Вид сделки")
    private DictionaryMultilangItemDto operationType;
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "ID вида операции", required = true)
    @NotNull(message = "Operation type must not be null")
    private Long operationTypeId;
    @ApiModelProperty(value = "ID типа объекта", required = true)
    private Long objectTypeId;

    @ApiModelProperty(value = "Общая информация о сделке продажи объекта")
    private ApplicationSellDataDto sellDataDto;

    @ApiModelProperty(value = "Общая информация о сделке покупки объекта")
    private ApplicationPurchaseDataDto purchaseDataDto;

    @ApiModelProperty(value = "Идентификационные данные сделки")
    private ContractFormDto contractDto;

    @ApiModelProperty(value = "Общая информация об объекте продажи")
    private RealPropertyDto realPropertyDto;

    @ApiModelProperty(value = "Общая информация об объекте покупки")
    private PurchaseInfoDto purchaseInfoDto;

    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
    @ApiModelProperty(value = "Логин Клиента")
    private String clientLogin;

    @ApiModelProperty(value = "Список доступных операций по текущей заявке")
    private List<String> operationList;

    @ApiModelProperty(value = "Дата регистрации заявки")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "ФИО")
    private String fullname;

    @ApiModelProperty(value = "Цена")
    private String price;

    @ApiModelProperty(value = "Статус")
    private DictionaryMultilangItemDto status;

    @ApiModelProperty(value = "Комментарий")
    private String comment;

    @ApiModelProperty(value = "Контакты агента")
    private String phone;


    public ApplicationDto(Application application) {
        this.setOperationType(DictionaryMappingTool.mapMultilangDictionary(application.getOperationType()));
        this.setCreationDate(application.getCreatedDate());
        this.setStatus(DictionaryMappingTool.mapMultilangDictionary(application.getApplicationStatus()));
        this.setComment(application.getApplicationSellData().getNote());
        this.setPhone(application.getCurrentAgent());
    }
}
