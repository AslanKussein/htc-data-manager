package kz.dilau.htcdatamanager.service.dictionary;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
