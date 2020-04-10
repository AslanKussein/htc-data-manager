package kz.dilau.htcdatamanager.domain;

import kz.dilau.htcdatamanager.domain.base.AuditableBaseEntity;
import kz.dilau.htcdatamanager.domain.dictionary.ApplicationStatus;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory extends AuditableBaseEntity<String, Long> {
    @ManyToOne
    @JoinColumn(name = "application_status_id", referencedColumnName = "id")
    private ApplicationStatus applicationStatus;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;
    private String comment;

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
