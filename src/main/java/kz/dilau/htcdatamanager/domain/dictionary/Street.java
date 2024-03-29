package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "street")
public class Street extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long parentId;

    private String kazPostId;
    @OneToOne
    @JoinColumn(name = "street_type_id")
    private StreetType streetType;
}
