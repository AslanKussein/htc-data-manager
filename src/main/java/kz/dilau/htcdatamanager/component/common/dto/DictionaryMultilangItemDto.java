package kz.dilau.htcdatamanager.component.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DictionaryMultilangItemDto", description = "Справочное значение")
public class DictionaryMultilangItemDto {
    public static final DictionaryMultilangItemDto NULL_OBJECT = null;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "Код")
    private String code;
    @ApiModelProperty(value = "Значение")
    private MultiLangText name;
}
