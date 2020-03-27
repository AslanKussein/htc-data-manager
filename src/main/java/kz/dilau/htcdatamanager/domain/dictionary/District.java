package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.BaseDictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = TABLE_NAME_PREFIX + "district")
public class District extends BaseDictionary<Long> {
    @Column(name = "code", nullable = false)
    private String code;
}
