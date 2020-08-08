package kz.dilau.htcdatamanager.web.dto.notification;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationDto {

    private Long notificationTypeId;
    private Long sellApplicationId;
    private Long buyApplicationId;
    private Long eventId;
    private Long notesId;

    public CreateNotificationDto(Long notificationTypeId, Long sellApplicationId) {
        this.notificationTypeId = notificationTypeId;
        this.sellApplicationId = sellApplicationId;
    }

    public CreateNotificationDto(Long notificationTypeId, Long sellApplicationId, Long buyApplicationId) {
        this.notificationTypeId = notificationTypeId;
        this.sellApplicationId = sellApplicationId;
        this.buyApplicationId = buyApplicationId;
    }

    public CreateNotificationDto(Long notificationTypeId, Long sellApplicationId, Long buyApplicationId, Long eventId) {
        this.notificationTypeId = notificationTypeId;
        this.sellApplicationId = sellApplicationId;
        this.buyApplicationId = buyApplicationId;
        this.eventId = eventId;
    }

}
