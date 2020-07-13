package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ApplicationContractInfoDto", description = "Модель информации заявки по договорам")
public class ApplicationContractInfoDto {
    @ApiModelProperty(value = "Идентификатор заявки")
    private Long applicationId;

    @ApiModelProperty(value = "Статус договора ОУ")
    private DictionaryMultilangItemDto contractStatus;

    @ApiModelProperty(value = "Признак наличия договора аванса/задатка")
    private Boolean hasDepositContract = false;
}
