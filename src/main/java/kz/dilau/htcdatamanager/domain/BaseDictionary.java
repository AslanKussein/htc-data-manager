package kz.dilau.htcdatamanager.domain;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDictionary<T> extends BaseEntity<T> {
    @Embedded
    private MultiLang multiLang;

    public MultiLang getMultiLang() {
        return multiLang;
    }

    public void setMultiLang(MultiLang multiLang) {
        this.multiLang = multiLang;
    }
}
