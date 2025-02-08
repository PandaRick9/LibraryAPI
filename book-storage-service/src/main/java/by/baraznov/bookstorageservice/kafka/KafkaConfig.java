package by.baraznov.bookstorageservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * KafkaConfig defines Kafka topic configurations for book-related events.
 */
@Configuration
public class KafkaConfig {
    /**
     * Defines the "addBook" Kafka topic.
     * @return NewTopic instance
     */
    @Bean
    public NewTopic newAddTopic(){
        return new NewTopic("addBook", 1, (short) 1);
    }

    /**
     * Defines the "deleteBook" Kafka topic.
     * @return NewTopic instance
     */
    @Bean
    public NewTopic newDeleteTopic(){
        return new NewTopic("deleteBook", 1, (short) 1);
    }
}