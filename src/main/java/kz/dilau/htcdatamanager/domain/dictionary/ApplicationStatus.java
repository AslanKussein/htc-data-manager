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
    public static final Long FAILED = 9L;
    public static final Long DEPOSIT = 10L;
    public static final Long APPROVAL_FOR_SUCCESS = 11L;
    public static final Long APPROVAL_FOR_FAILED = 12L;

    public static final List<Long> ALL_STATUSES = Arrays.asList(FIRST_CONTACT, MEETING, CONTRACT, ADS, PHOTO_SHOOT, DEMO, CLOSE_TRANSACTION, SUCCESS, FAILED);
    public static final List<Long> CLOSED_STATUSES = Arrays.asList(SUCCESS, FAILED);

    @Transient
    public boolean isContract() {
        return CONTRACT.equals(this.id);
    }

    @Transient
    public boolean isDeposit() {
        return DEPOSIT.equals(this.id);
    }
}
