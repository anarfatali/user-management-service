package az.company.usermanagementservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProducer {

    @Value("${spring.kafka.topics.user-created}")
    private String userCreatedTopic;

    @Value("${spring.kafka.topics.user-updated}")
    private String userUpdatedTopic;

    @Value("${spring.kafka.topics.user-deleted}")
    private String userDeletedTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private void sendAfterCommit(Object event, String topic) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    kafkaTemplate.send(topic, event);
                    log.info("Kafka event sent after commit: {}", event);
                }
            });
        } else {
            kafkaTemplate.send(topic, event);
            log.info("Kafka event sent immediately: {}", event);
        }
    }

    public void sendUserCreated(Object event) {
        sendAfterCommit(event, userCreatedTopic);
    }

    public void sendUserUpdated(Object event) {
        sendAfterCommit(event, userUpdatedTopic);
    }

    public void sendUserDeleted(Object event) {
        sendAfterCommit(event, userDeletedTopic);
    }
}
