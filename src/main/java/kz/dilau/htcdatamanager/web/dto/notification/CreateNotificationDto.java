package kz.dilau.htcdatamanager.web.dto.notification;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNotificationDto {

    private Long notificationTypeId;
    private Long sellApplicationId;
    private Long buyApplicationId;
    private Long eventId;
    private Long notesId;

    private Long applicationId1;
    private Long applicationId2;
    private String statusChangedAgent;

}
