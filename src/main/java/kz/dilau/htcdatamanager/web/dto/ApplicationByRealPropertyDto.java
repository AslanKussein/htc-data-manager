package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationByRealPropertyDto", description = "Унификация объекта в заявке")
public class ApplicationByRealPropertyDto {
    @ApiModelProperty(value = "ID заявки")
    private Long id;
    @ApiModelProperty(value = "Дата создания заявки")
    private ZonedDateTime creationDate;
    @ApiModelProperty(value = "Цена объекта")
    private BigDecimal objectPrice;
    @ApiModelProperty(value = "Логин агента, на кого назначена заявка")
    private String agent;
}
