package kz.dilau.htcdatamanager.web.dto.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.dilau.htcdatamanager.web.dto.common.DictionaryMultilangItemDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "DictionaryItemRequestDto", description = "Запрос на обновления справочного значения")
public class DictionaryItemRequestDto {
    public static final DictionaryItemRequestDto NULL_OBJECT = null;

    @ApiModelProperty(value = "Название справочника")
    private String dictionaryName;
    @ApiModelProperty(value = "Наименование на государственном языке")
    private String nameKz;
    @ApiModelProperty(value = "Наименование на английском языке")
    private String nameEn;
    @ApiModelProperty(value = "Наименование на русском языке")
    private String nameRu;
    @ApiModelProperty(value = "id родительского элемента")
    private Long parentId;
}
