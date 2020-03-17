package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.dictionary.City;
import kz.dilau.htcdatamanager.domain.dictionary.Street;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "htc_dm_real_property")
public class RealProperty extends BaseEntity<Long> {
    private City city;
    private Street street;

    @Column(unique = true)
    private String cadastralNumber;
    private Integer numberOfRooms;
    private String houseNumber;
}
