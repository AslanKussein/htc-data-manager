package kz.dilau.htcdatamanager.web.dto.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @ApiModelProperty(name = "objectPrice", value = "Цена объекта(млн тг)")
    private BigDecimal objectPrice;
    @ApiModelProperty(name = "probabilityOfBidding", value = "Вероятность торга")
    private Boolean probabilityOfBidding;//вероятность торга
    @ApiModelProperty(name = "exchange", value = "Обмен")
    private Boolean exchange;//обмен
    @ApiModelProperty(name = "mortgage", value = "Ипотека")
    private Boolean mortgage;//Продажа через ипотеку
    @ApiModelProperty(value = "Данные по невижимости", required = true)
    @NotNull(message = "Real property must not be null")
    private RealPropertyClientDto realPropertyClientDto;
    @ApiModelProperty(name = "note", value = "Примечание")
    private String note;
    @ApiModelProperty(name = "clientLogin", value = "Логин Клиента")
    private String clientLogin;
}
