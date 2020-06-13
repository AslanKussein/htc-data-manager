package kz.dilau.htcdatamanager.domain.dictionary;


import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Arrays;
import java.util.List;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "application_status")
public class ApplicationStatus extends BaseSystemDictionary {
    public static final Long FIRST_CONTACT = 1L;
    public static final Long MEETING = 2L;
    public static final Long CONTRACT = 3L;
    public static final Long ADS = 4L;
    public static final Long PHOTO_SHOOT = 5L;
    public static final Long DEMO = 6L;
    public static final Long CLOSE_TRANSACTION = 7L;
    public static final Long SUCCESS = 8L;
    public static final Long FINISHED = 9L;

    public static final List<Long> ALL_STATUSES = Arrays.asList(FIRST_CONTACT, MEETING, CONTRACT, ADS, PHOTO_SHOOT, DEMO, CLOSE_TRANSACTION, SUCCESS, FINISHED);

    @Transient
    public boolean isContract() {
        return CONTRACT.equals(this.id);
    }
}
