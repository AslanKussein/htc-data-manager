package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.domain.enums.ContractType;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractFormTemplateDto", description = "Модель формы договора")
public class ContractFormTemplateDto {
    @ApiModelProperty(value = "Код номера договора")
    private String code;
    @ApiModelProperty(value = "Тип договора")
    private ContractType contractType;
    @ApiModelProperty(value = "Шаблоны договора")
    private Map<String, String> templateMap = new HashMap<>();

    public Map<String, String> getTemplateMap() {
        if (isNull(templateMap)) {
            templateMap = new HashMap<>();
        }
        return templateMap;
    }
}
