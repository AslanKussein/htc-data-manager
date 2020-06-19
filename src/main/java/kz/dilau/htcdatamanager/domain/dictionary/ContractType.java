package kz.dilau.htcdatamanager.domain.dictionary;


import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "contract_type")
public class ContractType extends BaseSystemDictionary {
    public static final Long STANDARD = 1L;
    public static final Long EXCLUSIVE = 2L;
    public static final Long SUPER_EXCLUSIVE = 3L;
}
