package by.baraznov.booktrackerservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "addBook", groupId = "first_consumer")
    public void listenAddBook(String message){
        //
    }
    @KafkaListener(topics = "deleteBook", groupId = "first_consumer")
    public void listenDeleteBook(String message){
        System.out.println("Book id = " + message);
    }
}
