package by.baraznov.bookstorageservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newAddTopic(){
        return new NewTopic("addBook", 1, (short) 1);
    }
    @Bean
    public NewTopic newDeleteTopic(){
        return new NewTopic("deleteBook", 1, (short) 1);
    }

}
