package by.baraznov.bookstorageservice.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    void testSendAddMessage() {
        Integer bookId = 1;
        kafkaProducer.sendAddMessage(bookId);
        verify(kafkaTemplate).send("addBook", String.valueOf(bookId));
    }

    @Test
    void testSendDeleteMessage() {
        Integer bookId = 2;
        kafkaProducer.sendDeleteMessage(bookId);
        verify(kafkaTemplate).send("deleteBook", String.valueOf(bookId));
    }
}