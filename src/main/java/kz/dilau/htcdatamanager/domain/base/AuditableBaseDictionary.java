package kz.dilau.htcdatamanager.domain.base;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditableBaseDictionary<U, ID> extends AuditableBaseEntity<U, ID> {
    @Embedded
    private MultiLang multiLang;

    public MultiLang getMultiLang() {
        return multiLang;
    }

    public void setMultiLang(MultiLang multiLang) {
        this.multiLang = multiLang;
    }
}
