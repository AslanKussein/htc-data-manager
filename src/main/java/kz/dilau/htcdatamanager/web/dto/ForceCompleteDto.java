package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ForceCompleteDto", description = "Модель принудительного завершения сделки")
public class ForceCompleteDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "Обоснование")
    private String comment;
}
