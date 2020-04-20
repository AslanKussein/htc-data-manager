package kz.dilau.htcdatamanager.web.rest.vm.dictionary;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.domain.base.BaseDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDto {
    private Long id;
    private String nameKz;
    private String nameEn;
    private String nameRu;

    public DictionaryDto(BaseDictionary dictionary) {
        this.id = dictionary.getId();
        this.nameKz = dictionary.getMultiLang().getNameKz();
        this.nameEn = dictionary.getMultiLang().getNameEn();
        this.nameRu = dictionary.getMultiLang().getNameRu();
    }
}
