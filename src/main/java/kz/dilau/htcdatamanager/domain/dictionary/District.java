package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "district")
public class District extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Long parentId;
}
