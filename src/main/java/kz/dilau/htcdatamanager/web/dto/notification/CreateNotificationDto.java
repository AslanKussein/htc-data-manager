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

}
