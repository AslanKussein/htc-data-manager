package kz.dilau.htcdatamanager.domain.dictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_operation_type")
public class OperationType extends BaseDictionary<Long> {
}
