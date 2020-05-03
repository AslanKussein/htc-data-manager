package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "notes")
public class Notes extends AuditableBaseEntity<String, Long> {

    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_property_id")
    private RealProperty realProperty;
    /**
     * Флаг удаления
     */
    @Column(name = "is_removed", columnDefinition = "boolean default false")
    private Boolean deleted;
}
