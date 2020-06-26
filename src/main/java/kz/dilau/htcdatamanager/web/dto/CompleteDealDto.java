package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CompleteDealDto", description = "Модель завершения сделки")
public class CompleteDealDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "Идентификатор прикрепленного договора")
    private String contractGuid;

    @ApiModelProperty(value = "Идентификатор прикрепленного договора аванса/задатка")
    private String depositGuid;
}
