package kz.dilau.htcdatamanager.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaProducer(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //TODO: Пример вызова кафка продюсера из другого класса
    // @Autowired
    // private KafkaProducer kafkaProducer;
    // ...
    // String event;
    // kafkaProducer.sendMessage(topic, event);

    public void sendMessage(String topic, String event) {
        try {
            ListenableFuture<SendResult<Long, String>> future;
            future = kafkaTemplate.send(topic, event);
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