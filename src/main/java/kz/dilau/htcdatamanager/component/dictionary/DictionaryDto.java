package kz.dilau.htcdatamanager.component.dictionary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryDto<ID> {
    private ID id;
    private String nameKz;
    private String nameEn;
    private String nameRu;
    private String code;
    private String operationCode;
}
