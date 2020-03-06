package kz.dilau.htcdatamanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROPERTY_OWNER")
public class PropertyOwner {
    private String firstName;
    private String surname;
    private String patronymic;
    private String phoneNumber;
}
