package kz.dilau.htcdatamanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_property")
public class Property extends BaseEntity<Long> {
}
