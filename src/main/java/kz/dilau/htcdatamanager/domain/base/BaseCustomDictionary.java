package kz.dilau.htcdatamanager.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
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

    @JsonIgnore
    @Column(name = "is_removed", nullable = false, columnDefinition = "boolean default false")
    private Boolean isRemoved = false;
}
