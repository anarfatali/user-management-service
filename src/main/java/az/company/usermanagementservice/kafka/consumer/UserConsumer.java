package az.company.usermanagementservice.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.user-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserCreated(Object event) {
        log.info("Received USER_CREATED event: {}", event);
    }

    @KafkaListener(topics = "${spring.kafka.topics.user-updated}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserUpdated(Object event) {
        log.info("Received USER_UPDATED event: {}", event);
    }

    @KafkaListener(topics = "${spring.kafka.topics.user-deleted}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserDeleted(Object event) {
        log.info("Received USER_DELETED event: {}", event);
    }
}
