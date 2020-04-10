package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Entity
@Table(name = TABLE_NAME_PREFIX + "property_developer")
public class PropertyDeveloper extends BaseDictionary {
}
