package by.baraznov.bookstorageservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConfigTest {

    private final KafkaConfig kafkaConfig = new KafkaConfig();

    @Test
    void testNewAddTopic() {
        NewTopic topic = kafkaConfig.newAddTopic();
        assertNotNull(topic);
        assertEquals("addBook", topic.name());
        assertEquals(1, topic.numPartitions());
        assertEquals(1, topic.replicationFactor());
    }

    @Test
    void testNewDeleteTopic() {
        NewTopic topic = kafkaConfig.newDeleteTopic();
        assertNotNull(topic);
        assertEquals("deleteBook", topic.name());
        assertEquals(1, topic.numPartitions());
        assertEquals(1, topic.replicationFactor());
    }
}
