package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.service.NotificationService;
import kz.dilau.htcdatamanager.service.kafka.KafkaProducer;
import kz.dilau.htcdatamanager.web.dto.notification.CreateNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static kz.dilau.htcdatamanager.util.ObjectSerializer.introspect;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {


    private final DataProperties dataProperties;

    private final Long NOTIF_TYPE_NOTE_ADD = 1L;// Добавление комментария
    private final Long NOTIF_TYPE_NOTE_RESPONSE = 2L;//	Добавление ответа
    private final Long NOTIF_TYPE_APP_BUY_CREATE = 3L;// Создание заявки купить в КП
    private final Long NOTIF_TYPE_APP_SELL_CREATE = 4L;// Создание заявки продать в КП
    private final Long NOTIF_TYPE_BOOKING_PROPERTY = 5L;// Бронирование недвижимости
    private final Long NOTIF_TYPE_BOOKING_VIEW = 6L;// Бронь показа
    private final Long NOTIF_TYPE_OPERATION_IPOTEKA = 7L;//	Выполнение операции "Ипотека за 3 дня"
    private final Long NOTIF_TYPE_OPERATION_BUY_NOW = 8L;//	Выполнение операции "Купить сейчас"

    private final KafkaProducer kafkaProducer;

    @Override
    public void createNotesNotification(Long applicationId, String commentText) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(NOTIF_TYPE_NOTE_ADD, applicationId, commentText);

            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createBuyApplicationNotification(Long applicationId) {
        createNotification(NOTIF_TYPE_APP_BUY_CREATE, applicationId);
    }

    @Override
    public void createSellApplicationNotification(Long applicationId) {
        createNotification(NOTIF_TYPE_APP_SELL_CREATE, applicationId);
    }

    @Override
    public void createIpotekaNotification(Long applicationId, Long eventId) {
        createNotificationWithEventId(NOTIF_TYPE_OPERATION_IPOTEKA, applicationId, eventId);
    }

    @Override
    public void createBookingViewNotification(Long applicationId, Long eventId) {
        createNotificationWithEventId(NOTIF_TYPE_BOOKING_VIEW, applicationId, eventId);
    }

    @Override
    public void createBookingPropertyNotification(Long applicationId) {
        createNotification(NOTIF_TYPE_BOOKING_PROPERTY, applicationId);
    }

    @Override
    public void createBuyNowNotification(Long applicationId) {
        createNotification(NOTIF_TYPE_OPERATION_BUY_NOW, applicationId);
    }


    private void createNotification(Long notificationTypeId, Long applicationId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(notificationTypeId, applicationId);
            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createNotificationWithEventId(Long notificationTypeId, Long applicationId, Long eventId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(notificationTypeId, applicationId, eventId);

            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
