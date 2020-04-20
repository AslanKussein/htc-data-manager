package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "type_of_elevator")
public class TypeOfElevator extends BaseCustomDictionary {
}
