package kz.dilau.htcdatamanager.domain.base;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseCustomDictionary extends BaseEntity<Long> {
    @Embedded
    private MultiLang multiLang;

    public MultiLang getMultiLang() {
        return multiLang;
    }

    public void setMultiLang(MultiLang multiLang) {
        this.multiLang = multiLang;
    }
}
