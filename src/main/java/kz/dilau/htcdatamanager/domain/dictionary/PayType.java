package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "pay_type")
public class PayType extends BaseCustomDictionary {

    public static final Long BOOKING = 1L;
    public static final Long BUY_THREE_PRC = 2L;
    public static final Long BUY_FULL = 3L;
    public static final Long DEPOSIT = 4L;
    public static final Long PREPAYMENT = 5L;
}
