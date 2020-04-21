package kz.dilau.htcdatamanager.domain.dictionary;


import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "operation_type")
public class OperationType extends BaseSystemDictionary {
    public static final String BUY = "001001";
    public static final String SELL = "001002";
}
