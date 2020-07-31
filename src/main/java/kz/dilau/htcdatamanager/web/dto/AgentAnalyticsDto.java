package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AgentAnalyticsDto", description = "Модель аналитики агента")
public class AgentAnalyticsDto {
    @NonNull
    @ApiModelProperty(value = "Логин агента")
    private String agentLogin;
    @NonNull
    @ApiModelProperty(value = "Типы аналитики для расчета")
    private List<String> analyticsTypes;
}
