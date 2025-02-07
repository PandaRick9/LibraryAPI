package by.baraznov.bookstorageservice.kafka;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendAddMessage(Integer id){
        kafkaTemplate.send("addBook", String.valueOf(id));
    }
    public void sendDeleteMessage(Integer id){
        kafkaTemplate.send("deleteBook", String.valueOf(id));
    }

}
