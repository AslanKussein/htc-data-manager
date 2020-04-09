package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htccommons.domain.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Entity
@Table(name = TABLE_NAME_PREFIX + "operation_type")
public class OperationType extends BaseSystemDictionary<Long> {
}
