package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

/**
 * Справочник всех справочников
 */

@Data
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "all_dict")
public class AllDict extends BaseSystemDictionary {
    @Column(name = "is_editable", nullable = false, columnDefinition = "boolean default true")
    private Boolean isEditable = false;
}
