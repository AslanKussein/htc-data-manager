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

    private final Long NOTIF_TYPE_NOTE_ADD = 1L;// Добавление комментария
    private final Long NOTIF_TYPE_NOTE_RESPONSE = 2L;//	Добавление ответа
    private final Long NOTIF_TYPE_APP_BUY_CREATE = 3L;// Создание заявки купить в КП
    private final Long NOTIF_TYPE_APP_SELL_CREATE = 4L;// Создание заявки продать в КП
    private final Long NOTIF_TYPE_BOOKING_PROPERTY = 5L;// Бронирование недвижимости
    private final Long NOTIF_TYPE_BOOKING_VIEW = 6L;// Бронь показа
    private final Long NOTIF_TYPE_OPERATION_IPOTEKA = 7L;//	Выполнение операции "Ипотека за 3 дня"
    private final Long NOTIF_TYPE_OPERATION_BUY_NOW = 8L;//	Выполнение операции "Купить сейчас"

    private final KafkaProducer kafkaProducer;
    private final DataProperties dataProperties;

    @Override
    public void createNotesNotification(Long sellApplicationId, Long notesId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(NOTIF_TYPE_NOTE_ADD, sellApplicationId);
            dto.setNotesId(notesId);

            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createNotesAnswerNotification(Long sellApplicationId, Long notesId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(NOTIF_TYPE_NOTE_RESPONSE, sellApplicationId);
            dto.setNotesId(notesId);

            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void createBuyApplicationNotification(Long sellApplicationId) {
        createNotification(NOTIF_TYPE_APP_BUY_CREATE, sellApplicationId);
    }

    @Override
    public void createSellApplicationNotification(Long sellApplicationId) {
        createNotification(NOTIF_TYPE_APP_SELL_CREATE, sellApplicationId);
    }

    @Override
    public void createIpotekaNotification(Long sellApplicationId, Long eventId) {
        createNotificationWithEventId(NOTIF_TYPE_OPERATION_IPOTEKA, sellApplicationId, null, eventId);
    }

    @Override
    public void createBookingViewNotification(Long sellApplicationId, Long eventId) {
        createNotificationWithEventId(NOTIF_TYPE_BOOKING_VIEW, sellApplicationId, null, eventId);
    }

    @Override
    public void createBookingPropertyNotification(Long sellApplicationId, Long buyApplicationId) {
        createNotification(NOTIF_TYPE_BOOKING_PROPERTY, sellApplicationId, buyApplicationId);
    }

    @Override
    public void createBuyNowNotification(Long sellApplicationId, Long buyApplicationId) {
        createNotification(NOTIF_TYPE_OPERATION_BUY_NOW, sellApplicationId, buyApplicationId);
    }

    private void createNotification(Long notificationTypeId, Long sellApplicationId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(notificationTypeId, sellApplicationId);
            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createNotification(Long notificationTypeId, Long sellApplicationId, Long buyApplicationId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(notificationTypeId, sellApplicationId, buyApplicationId);
            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createNotificationWithEventId(Long notificationTypeId, Long sellApplicationId, Long buyApplicationId, Long eventId) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto(notificationTypeId, sellApplicationId, buyApplicationId, eventId);

            kafkaProducer.sendMessage(dataProperties.getTopicNotification(), introspect(dto));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
