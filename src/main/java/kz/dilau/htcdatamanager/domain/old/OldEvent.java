package kz.dilau.htcdatamanager.domain.old;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
import kz.dilau.htcdatamanager.domain.old.OldApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "old_event")
public class OldEvent extends AuditableBaseEntity<String, Long> {
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;
    @ManyToOne
    @JoinColumn(name = "source_application_id")
    private OldApplication sourceApplication;
    @Column(name = "source_application_id", insertable = false, updatable = false)
    private Long sourceApplicationId;
    @ManyToOne
    @JoinColumn(name = "target_application_id")
    private OldApplication targetApplication;
    @Column(name = "target_application_id", insertable = false, updatable = false)
    private Long targetApplicationId;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
}
