package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ForceCloseDealDto", description = "Модель принудительного завершения сделки")
public class ForceCloseDealDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "Обоснование")
    private String comment;

    @ApiModelProperty(value = "Признак успешного закрытия")
    private boolean isApprove = false;

    @ApiModelProperty(value = "ID связанной заявки", required = true)
    private Long targetApplicationId;
}
