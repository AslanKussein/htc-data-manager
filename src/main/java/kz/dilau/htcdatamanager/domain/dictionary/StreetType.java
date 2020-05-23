package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.MultiLang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "street_type")
public class StreetType {

    @Id
    private String id;
    @Embedded
    private MultiLang multiLang;
}