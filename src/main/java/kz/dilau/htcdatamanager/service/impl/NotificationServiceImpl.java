package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.Event;
import kz.dilau.htcdatamanager.service.ApplicationService;
import kz.dilau.htcdatamanager.service.EventService;
import kz.dilau.htcdatamanager.service.NotificationService;
import kz.dilau.htcdatamanager.service.kafka.KafkaProducer;
import kz.dilau.htcdatamanager.web.dto.notification.CreateNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;
import static kz.dilau.htcdatamanager.util.ObjectSerializer.introspect;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final Long NOTIF_TYPE_NOTE_ADD = 1L;// Добавление комментария
    private final Long NOTIF_TYPE_NOTE_RESPONSE = 2L;//	Добавление ответа
    private final Long NOTIF_TYPE_APP_BUY_CREATE = 3L;// Создание заявки купить в КП
    private final Long NOTIF_TYPE_APP_SELL_CREATE = 4L;// Создание заявки продать в КП
    private final Long NOTIF_TYPE_BOOKING_PROPERTY = 5L;// Бронирование недвижимости
    private final Long NOTIF_TYPE_BOOKING_VIEW = 6L;// Бронь показа
    private final Long NOTIF_TYPE_OPERATION_IPOTEKA = 7L;//	Выполнение операции "Ипотека за 3 дня"
    private final Long NOTIF_TYPE_OPERATION_BUY_NOW = 8L;//	Выполнение операции "Купить сейчас"

    private final Long NOTIF_TYPE_OPERATION_ASSIGNED_OR_REASSIGNED = 9L;//	Назначенные/переназначенные новые заявки агенту
    private final Long NOTIF_TYPE_OPERATION_DEAL_CLOSING_APPROVAL = 10L;// Согласование закрытия сделки
    private final Long NOTIF_TYPE_OPERATION_COMPLETED_EVENT_RELATED_TICKET = 11L;// Завершена заявка связанная по событию
    private final Long NOTIF_TYPE_OPERATION_LINKED_TICKET_COMPLETED = 12L;// Связанная заявка завершена


    private final KafkaProducer kafkaProducer;
    private final DataProperties dataProperties;

    @Override
    public void createNotesNotification(Long sellApplicationId, Long notesId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_NOTE_ADD)
                .sellApplicationId(sellApplicationId)
                .notesId(notesId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createNotesAnswerNotification(Long sellApplicationId, Long notesId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_NOTE_RESPONSE)
                .sellApplicationId(sellApplicationId)
                .notesId(notesId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createBuyApplicationNotification(Long sellApplicationId) {

        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_APP_BUY_CREATE)
                .sellApplicationId(sellApplicationId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createSellApplicationNotification(Long sellApplicationId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_APP_SELL_CREATE)
                .sellApplicationId(sellApplicationId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createIpotekaNotification(Long sellApplicationId, Long eventId) {

        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_OPERATION_IPOTEKA)
                .sellApplicationId(sellApplicationId)
                .eventId(eventId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createBookingViewNotification(Long sellApplicationId, Long eventId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_BOOKING_VIEW)
                .sellApplicationId(sellApplicationId)
                .eventId(eventId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createBookingPropertyNotification(Long sellApplicationId, Long buyApplicationId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_BOOKING_PROPERTY)
                .sellApplicationId(sellApplicationId)
                .buyApplicationId(buyApplicationId)
                .build();
        sendMessage(dto);
    }

    @Override
    public void createBuyNowNotification(Long sellApplicationId, Long buyApplicationId) {
        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_OPERATION_BUY_NOW)
                .sellApplicationId(sellApplicationId)
                .buyApplicationId(buyApplicationId)
                .build();
        sendMessage(dto);
    }

    private void sendMessage(CreateNotificationDto dto) {
        try {
            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createApplicationAssignedNotification(Long applicationId1) {

        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_OPERATION_ASSIGNED_OR_REASSIGNED)
                .applicationId1(applicationId1)
                .build();

        sendMessage(dto);
    }

    @Override
    public void createApplicationDealClosingApproval(Long applicationId1, String statusChangedAgent) {

        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_OPERATION_DEAL_CLOSING_APPROVAL)
                .applicationId1(applicationId1)
                .statusChangedAgent(statusChangedAgent)
                .build();

        sendMessage(dto);
    }

    @Override
    public void createCompletedEventRelatedApplication(Event event) {


        CreateNotificationDto dto = CreateNotificationDto.builder()
                .notificationTypeId(NOTIF_TYPE_OPERATION_COMPLETED_EVENT_RELATED_TICKET)
                .eventId(event.getId())
                .applicationId1(event.getSourceApplicationId())
                .applicationId2(event.getTargetApplicationId())
                .build();
        sendMessage(dto);

    }

    @Override
    public void createCompletedLinkedTicketApplication(Application application) {

        if (nonNull(application.getTargetApplication())) {
            CreateNotificationDto dto = CreateNotificationDto.builder()
                    .notificationTypeId(NOTIF_TYPE_OPERATION_LINKED_TICKET_COMPLETED)
                    .applicationId1(application.getId())
                    .applicationId2(application.getTargetApplication().getId())
                    .build();
            sendMessage(dto);
        }

    }

}
