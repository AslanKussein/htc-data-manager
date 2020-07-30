package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.service.NotificationService;
import kz.dilau.htcdatamanager.service.kafka.KafkaProducer;
import kz.dilau.htcdatamanager.web.dto.notification.CreateNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final Long NOTIF_TYPE_NOTE_ADD = 1L;//	Добавление комментария
    private final Long NOTIF_TYPE_NOTE_RESPONSE = 2L;//	Добавление ответа
    private final Long NOTIF_TYPE_APP_BUY_CREATE = 3L;//	Создание заявки купить в КП
    private final Long NOTIF_TYPE_APP_SALE_CREATE = 4L;//	Создание заявки продать в КП
    private final Long NOTIF_TYPE_BOOKING_PROPERTY = 5L;//	Бронирование недвижимости
    private final Long NOTIF_TYPE_BOOKING_VIEW = 6L;//	Бронь показа
    private final Long NOTIF_TYPE_OPERATION_IPOTEKA = 7L;//	Выполнение операции "Ипотека за 3 дня"
    private final Long NOTIF_TYPE_OPERATION_BUY_NOW = 8L;//	Выполнение операции "Купить сейчас"
    @Autowired

    private KafkaProducer kafkaProducer;

    @Override
    public void createNotesNoification(Long applicationId, String commentText) {
        try {
            CreateNotificationDto dto = new CreateNotificationDto();
            dto.setNotificationTypeId(NOTIF_TYPE_NOTE_ADD);
            dto.setApplicationId(applicationId);
            dto.setCommentText(commentText);

            Map<String, Object> event = introspect(dto);
            kafkaProducer.sendMessage("notification", event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> introspect(CreateNotificationDto obj) throws Exception {
        Map<String, Object> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                result.put(pd.getName(), reader.invoke(obj));
        }
        return result;
    }
}
