package kz.dilau.htcdatamanager.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseCustomDictionaryWithCode extends BaseCustomDictionary {
    @Column(name = "code")
    private String code;
}
