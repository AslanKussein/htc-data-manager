package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "object_type")
public class ObjectType extends BaseSystemDictionary {
    public static final Long HOUSE = 2L;
}
