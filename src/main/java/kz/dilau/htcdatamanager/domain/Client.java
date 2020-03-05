package kz.dilau.htcdatamanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client {
    private String firstName;
    private String surname;
    private String patronymic;
    private String phoneNumber;
}
