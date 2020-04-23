package kz.dilau.htcdatamanager.web.dto;

import kz.dilau.htcdatamanager.domain.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventDto {
    private Long id;
    private Date eventDate;
    private Long eventTypeId;
    private String description;
    private String comment;
    private Long clientId;
    private Long sourceApplicationId;
    private Long targetApplicationId;

    public EventDto() {
    }

    public EventDto(Long id, Date eventDate, Long eventTypeId, String description, String comment, Long clientId, Long sourceApplicationId, Long targetApplicationId) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventTypeId = eventTypeId;
        this.description = description;
        this.comment = comment;
        this.clientId = clientId;
        this.sourceApplicationId = sourceApplicationId;
        this.targetApplicationId = targetApplicationId;
    }

    public EventDto(Event event) {
        this.id = event.getId();
        this.eventDate = event.getEventDate();
        this.eventTypeId = event.getEventType().getId();
        this.sourceApplicationId = event.getSourceApplication().getId();
        if (event.getTargetApplication() != null) {
            this.targetApplicationId = event.getTargetApplication().getId();
        }
        this.description = description;
        this.comment = comment;
        this.clientId = clientId;
    }
}
