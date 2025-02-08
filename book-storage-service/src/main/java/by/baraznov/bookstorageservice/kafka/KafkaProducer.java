package by.baraznov.bookstorageservice.kafka;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * KafkaProducer handles sending messages to Kafka topics for book addition and deletion events.
 */
@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the "addBook" Kafka topic.
     * @param id The ID of the book being added
     */
    public void sendAddMessage(Integer id){
        kafkaTemplate.send("addBook", String.valueOf(id));
    }

    /**
     * Sends a message to the "deleteBook" Kafka topic.
     * @param id The ID of the book being deleted
     */
    public void sendDeleteMessage(Integer id){
        kafkaTemplate.send("deleteBook", String.valueOf(id));
    }
}