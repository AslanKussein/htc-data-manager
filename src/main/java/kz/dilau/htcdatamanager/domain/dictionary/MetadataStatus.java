package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseSystemDictionary;

import javax.persistence.Entity;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "metadata_status")
public class MetadataStatus extends BaseSystemDictionary {
    public static final Long APPROVED = 1L;
    public static final Long NOT_APPROVED = 2L;
    public static final Long ARCHIVE = 3L;
    public static final Long REJECTED = 4L;
}
