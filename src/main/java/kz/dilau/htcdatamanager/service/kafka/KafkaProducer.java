package kz.dilau.htcdatamanager.service.kafka;

import kz.dilau.htcdatamanager.config.DataProperties;
import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.RealProperty;
import kz.dilau.htcdatamanager.domain.RealPropertyMetadata;
import kz.dilau.htcdatamanager.domain.dictionary.MetadataStatus;
import kz.dilau.htcdatamanager.domain.enums.AnalyticsType;
import kz.dilau.htcdatamanager.util.ObjectSerializer;
import kz.dilau.htcdatamanager.web.dto.AgentAnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.RealPropertyAnalyticsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class KafkaProducer {
    private final DataProperties dataProperties;
    private final KafkaTemplate<Long, Map<String, Object>> kafkaTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaProducer(KafkaTemplate<Long, Map<String, Object>> kafkaTemplate, DataProperties dataProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.dataProperties = dataProperties;
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

    public void sendRealPropertyAnalytics(Application application) {
        if (nonNull(application) && application.getOperationType().isSell() && nonNull(application.getApplicationSellData())
                && nonNull(application.getApplicationSellData().getRealProperty()) && nonNull(application.getApplicationSellData().getRealProperty().getBuilding())) {
            RealProperty realProperty = application.getApplicationSellData().getRealProperty();
            RealPropertyMetadata metadata = realProperty.getMetadataByStatus(MetadataStatus.APPROVED);
            RealPropertyAnalyticsDto realPropertyAnalyticsDto = RealPropertyAnalyticsDto.builder()
                    .buildingId(realProperty.getBuilding().getId())
                    .districtId(realProperty.getBuilding().getDistrictId())
                    .houseClassId(nonNull(metadata) && nonNull(metadata.getGeneralCharacteristics()) ? metadata.getGeneralCharacteristics().getHouseClassId() : null)
                    .build();
            sendMessage(dataProperties.getTopicRealProperty(), ObjectSerializer.introspect(realPropertyAnalyticsDto));
        }
    }

    public void sendAllAgentAnalytics(String agentLogin) {
        List<String> analyticsTypes = new ArrayList<>();
        analyticsTypes.add(AnalyticsType.CONTRACT_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.DEPOSIT_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.SALE_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.RATING.name());
        sendAgentAnalytics(agentLogin, analyticsTypes);
    }

    public void sendRatingAgentAnalytics(String agentLogin) {
        List<String> analyticsTypes = new ArrayList<>();
        analyticsTypes.add(AnalyticsType.RATING.name());
        sendAgentAnalytics(agentLogin, analyticsTypes);
    }

    public void sendContractAgentAnalytics(String agentLogin) {
        List<String> analyticsTypes = new ArrayList<>();
        analyticsTypes.add(AnalyticsType.CONTRACT_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.SALE_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.RATING.name());
        sendAgentAnalytics(agentLogin, analyticsTypes);
    }

    public void sendDepositAgentAnalytics(String agentLogin) {
        List<String> analyticsTypes = new ArrayList<>();
        analyticsTypes.add(AnalyticsType.DEPOSIT_AMOUNT.name());
        analyticsTypes.add(AnalyticsType.RATING.name());
        sendAgentAnalytics(agentLogin, analyticsTypes);
    }

    private void sendAgentAnalytics(String agentLogin, List<String> analyticsTypes) {
        sendMessage(dataProperties.getTopicAnalyticAgent(), ObjectSerializer.introspect(AgentAnalyticsDto.builder()
                .agentLogin(agentLogin)
                .analyticsTypes(analyticsTypes)
                .build()));
    }
}