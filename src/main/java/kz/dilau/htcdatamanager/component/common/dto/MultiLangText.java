package kz.dilau.htcdatamanager.component.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiLangText {
    public static final MultiLangText NULL_OBJECT = new MultiLangText();

    public MultiLangText(String nameRu) {
        this.nameRu = nameRu;
        this.nameKz = nameRu;
        this.nameEn = nameRu;
    }

    private String nameRu;
    private String nameKz;
    private String nameEn;
}
