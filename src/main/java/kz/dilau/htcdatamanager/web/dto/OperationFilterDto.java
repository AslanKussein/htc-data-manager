package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OperationFilterDto", description = "Модель фильтрации операций по принадлежности к заявке")
public class OperationFilterDto {
    @ApiModelProperty(value = "Автор заявки")
    private String author;
    @ApiModelProperty(value = "Назначенный агент")
    private String currentAgent;
    @ApiModelProperty(value = "Список групп операций")
    private List<String> operationGroupList;

    public List<String> getOperationGroupList() {
        if (isNull(operationGroupList)) {
            operationGroupList = new ArrayList<>();
        }
        return operationGroupList;
    }
}
