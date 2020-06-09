package kz.dilau.htcdatamanager.domain.dictionary;

import kz.dilau.htcdatamanager.domain.base.BaseCustomDictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.DICTIONARY_TABLE_NAME_PREFIX;

@Getter
@Setter
@Entity
@Table(name = DICTIONARY_TABLE_NAME_PREFIX + "material_of_construction")
public class MaterialOfConstruction extends BaseCustomDictionary {
    @ManyToOne
    @JoinColumn(name = "object_type_id")
    private ObjectType objectType;

    @Column(name = "object_type_id", insertable = false, updatable = false)
    private Long parentId;
}
