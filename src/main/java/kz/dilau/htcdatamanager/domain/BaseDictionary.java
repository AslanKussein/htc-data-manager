package kz.dilau.htcdatamanager.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDictionary<T> extends BaseEntity<T> {
    @JsonUnwrapped
    @Embedded
    private MultiLang multiLang;

    public MultiLang getMultiLang() {
        return multiLang;
    }

    public void setMultiLang(MultiLang multiLang) {
        this.multiLang = multiLang;
    }
}
