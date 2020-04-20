package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.EventType;
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
@Table(name = TABLE_NAME_PREFIX + "event")
public class Event extends BaseEntity<Long> {
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    @ManyToOne
    @JoinColumn(name = "event_type")
    private EventType eventType;
    @ManyToOne
    @JoinColumn(name = "app_id")
    private Application application;
    @ManyToOne
    @JoinColumn(name = "app_id2")
    private Application application2;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @Column(name = "client_id")
    private Long clientId;
}
