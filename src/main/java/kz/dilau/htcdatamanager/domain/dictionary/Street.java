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
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "street")
public class Street extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
