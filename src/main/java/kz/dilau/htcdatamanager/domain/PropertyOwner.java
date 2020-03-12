package kz.dilau.htcdatamanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_property_owner")
public class PropertyOwner extends BaseEntity<Long> {
    private String firstName;
    private String surname;
    private String patronymic;
    private String phoneNumber;
}
