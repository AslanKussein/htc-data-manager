package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ConfirmDealDto", description = "Модель согласования завершения сделки")
public class ConfirmDealDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;

    @NonNull
    @ApiModelProperty(value = "Признак согласования завершения сделки")
    private Boolean approve;

    @ApiModelProperty(value = "Идентификатор документа, подтверждающего завершение сделки")
    private String guid;
}
