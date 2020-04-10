package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.domain.Event;

import java.util.Date;
import java.util.List;

public class EventDto {
    private Long id;
    private Date eventDate;
    private Long applicationStatusId;
    private List<Long> applicationsIds;
    private List<Long> realPropertiesIds;
    private String description;
    private String comment;
    private Long clientId;

    public EventDto() {
    }

    public EventDto(Long id, Date eventDate, Long applicationStatusId, List<Long> applicationsIds, List<Long> realPropertiesIds, String description, String comment, Long clientId) {
        this.id = id;
        this.eventDate = eventDate;
        this.applicationStatusId = applicationStatusId;
        this.applicationsIds = applicationsIds;
        this.realPropertiesIds = realPropertiesIds;
        this.description = description;
        this.comment = comment;
        this.clientId = clientId;
    }

    public EventDto(Event event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.applicationStatusId = event.getApplicationStatus().getId();
        this.applicationsIds = applicationsIds;
        this.realPropertiesIds = realPropertiesIds;
        this.description = description;
        this.comment = comment;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Long getApplicationStatusId() {
        return applicationStatusId;
    }

    public void setApplicationStatusId(Long applicationStatusId) {
        this.applicationStatusId = applicationStatusId;
    }

    public List<Long> getApplicationsIds() {
        return applicationsIds;
    }

    public void setApplicationsIds(List<Long> applicationsIds) {
        this.applicationsIds = applicationsIds;
    }

    public List<Long> getRealPropertiesIds() {
        return realPropertiesIds;
    }

    public void setRealPropertiesIds(List<Long> realPropertiesIds) {
        this.realPropertiesIds = realPropertiesIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
