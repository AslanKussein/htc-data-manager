package kz.dilau.htcdatamanager.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ContractTemplateDto", description = "Модель формы договора")
public class ContractTemplateDto {

    @ApiModelProperty(value = "Наименование")
    private String name;

    @ApiModelProperty(value = "Шаблон")
    private String template;

    @ApiModelProperty(value = "Параметры")
    private List<String> parList = new ArrayList<>();

}
