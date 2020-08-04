package kz.dilau.htcdatamanager.web.dto.notification;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationDto {

    private Long notificationTypeId;
    private Long applicationId;
    private Long eventId;
    private String commentText;

    public CreateNotificationDto(Long notificationTypeId, Long applicationId) {
        this.notificationTypeId = notificationTypeId;
        this.applicationId = applicationId;
    }

    public CreateNotificationDto(Long notificationTypeId, Long applicationId, Long eventId) {
        this.notificationTypeId = notificationTypeId;
        this.applicationId = applicationId;
        this.eventId = eventId;
    }

    public CreateNotificationDto(Long notificationTypeId, Long applicationId, String commentText) {
        this.notificationTypeId = notificationTypeId;
        this.applicationId = applicationId;
        this.commentText = commentText;
    }
}
