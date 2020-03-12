package kz.dilau.htcdatamanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_application")
public class Application extends BaseEntity<Long> {
}
