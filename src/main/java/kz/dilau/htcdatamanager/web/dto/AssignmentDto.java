package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель для переназначения заявки агенту")
public class AssignmentDto {
    @NonNull
    @ApiModelProperty(value = "ID заявки", required = true)
    private Long applicationId;

    @NonNull
    @ApiModelProperty(value = "Логин агента, на кого переназначается заявка", required = true)
    private String agent;
}
