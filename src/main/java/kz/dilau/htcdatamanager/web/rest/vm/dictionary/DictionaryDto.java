package kz.dilau.htcdatamanager.web.rest.vm.dictionary;

import io.swagger.annotations.ApiModel;
import kz.dilau.htcdatamanager.domain.base.BaseDictionary;

@ApiModel
public class DictionaryDto {
    private Long id;
    private String nameKz;
    private String nameEn;
    private String nameRu;

    public DictionaryDto() {
    }

    public DictionaryDto(Long id, String nameKz, String nameEn, String nameRu) {
        this.id = id;
        this.nameKz = nameKz;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    public DictionaryDto(BaseDictionary dictionary) {
        this.id = dictionary.getId();
        this.nameKz = dictionary.getMultiLang().getNameKz();
        this.nameEn = dictionary.getMultiLang().getNameEn();
        this.nameRu = dictionary.getMultiLang().getNameRu();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameKz() {
        return nameKz;
    }

    public void setNameKz(String nameKz) {
        this.nameKz = nameKz;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }
}
