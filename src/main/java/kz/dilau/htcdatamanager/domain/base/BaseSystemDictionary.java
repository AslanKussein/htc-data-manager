package kz.dilau.htcdatamanager.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseSystemDictionary extends BaseCustomDictionary {
    @Column(name = "code", unique = true, nullable = false)
    protected String code;
}
