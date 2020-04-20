package kz.dilau.htcdatamanager.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseSystemDictionary extends BaseDictionary {
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean isEnabled = true;
    @Column(name = "is_removed", nullable = false, columnDefinition = "boolean default false")
    private boolean isRemoved = false;
}
