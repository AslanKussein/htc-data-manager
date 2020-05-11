package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "district")
public class District extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
