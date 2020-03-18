package kz.dilau.htcdatamanager.domain.dictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_zhk")
public class ZhK extends BaseDictionary<Long> {
}
