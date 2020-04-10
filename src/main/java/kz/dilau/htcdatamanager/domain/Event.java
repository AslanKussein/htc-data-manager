package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.BaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "event_type", referencedColumnName = "id")
    private ApplicationStatus applicationStatus;
    @ElementCollection
    @CollectionTable(name = "event_applications", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "application_id")
    private List<Long> applicationsIds;
    @ElementCollection
    @CollectionTable(name = "event_properties", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "real_property_id")
    private List<Long> realPropertiesIds;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;
    @Column(name = "client_id")
    private Long clientId;
}
