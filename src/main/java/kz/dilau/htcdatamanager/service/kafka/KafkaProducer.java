package kz.dilau.htcdatamanager.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<Long, Map<String, Object>> kafkaTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaProducer(KafkaTemplate<Long, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //TODO: Пример вызова кафка продюсера из другого класса
    // @Autowired
    // private KafkaProducer kafkaProducer;
    // ...
    // Map<String, Object> event;
    // kafkaProducer.sendMessage(topic, event);

    public void sendMessage(String topic, Map<String, Object> event) {
        try {
            ListenableFuture<SendResult<Long, Map<String, Object>>> future;
            if ((topic == null) || topic.isEmpty()) {
                future = kafkaTemplate.sendDefault(event);
            } else {
                future = kafkaTemplate.send(topic, event);
            }
            future.get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            // restore interrupted status
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

}