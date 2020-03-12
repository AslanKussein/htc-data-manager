package kz.dilau.htcdatamanager.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MultiLang implements Serializable {
    private String nameKz;
    private String nameRu;
    private String nameEn;

    public MultiLang() {
    }

    public MultiLang(String nameKz, String nameRu, String nameEn) {
        this.nameKz = nameKz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    @Column(name = "name_kz", nullable = false)
    public String getNameKz() {
        return nameKz;
    }

    public void setNameKz(String nameKz) {
        this.nameKz = nameKz;
    }

    @Column(name = "name_ru", nullable = false)
    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    @Column(name = "name_en", nullable = false)
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
