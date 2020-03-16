package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.BaseEntity;
import kz.dilau.htcdatamanager.domain.MultiLang;

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
