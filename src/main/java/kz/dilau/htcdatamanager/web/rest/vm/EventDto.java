package kz.dilau.htcdatamanager.web.rest.vm;

import kz.dilau.htcdatamanager.domain.Event;

import java.util.Date;

public class EventDto {
    private Long id;
    private Date eventDate;
    private Long eventTypeId;
    private String description;
    private String comment;
    private Long clientId;
    private Long applicationId;
    private Long applicationId2;

    public EventDto() {
    }

    public EventDto(Long id, Date eventDate, Long eventTypeId, String description, String comment, Long clientId, Long applicationId, Long applicationId2) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventTypeId = eventTypeId;
        this.description = description;
        this.comment = comment;
        this.clientId = clientId;
        this.applicationId = applicationId;
        this.applicationId2 = applicationId2;
    }

    public EventDto(Event event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.eventTypeId = event.getEventType().getId();
        this.applicationId = event.getApplication().getId();
        if (event.getApplication2() != null) {
            this.applicationId2 = event.getApplication2().getId();
        }
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

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getApplicationId2() {
        return applicationId2;
    }

    public void setApplicationId2(Long applicationId2) {
        this.applicationId2 = applicationId2;
    }
}
