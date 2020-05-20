package kz.dilau.htcdatamanager.domain.old;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static kz.dilau.htcdatamanager.config.Constants.TABLE_NAME_PREFIX;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TABLE_NAME_PREFIX + "old_assignment")
public class OldAssignment extends AuditableBaseEntity<String, Long> {
    @Column(name = "agent")
    private String agent;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private OldApplication application;
}
