package kz.dilau.htcdatamanager.web.dto.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.PageableDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "DictionaryFilterDto", description = "Запрос на получение справочных значений")
public class DictionaryFilterDto {
    public static final DictionaryFilterDto NULL_OBJECT = null;

    @ApiModelProperty(value = "Название справочника")
    private String dictionaryName;

    @ApiModelProperty(value = "Названия справочника")
    private PageableDto pageableDto;
}
