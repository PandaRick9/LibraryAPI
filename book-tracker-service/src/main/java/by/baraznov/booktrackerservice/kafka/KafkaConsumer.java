package by.baraznov.booktrackerservice.kafka;

import by.baraznov.booktrackerservice.model.BookInformation;
import by.baraznov.booktrackerservice.repository.BookInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
/**
 * Kafka consumer for processing book-related messages.
 */
@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final long AMOUNT_OF_WEEKS = 1L;
    private final BookInformationRepository bookInformationRepository;

    /**
     * Listens to the "addBook" Kafka topic and adds a new book record.
     * @param message The book ID received as a string.
     */
    @KafkaListener(topics = "addBook", groupId = "first_consumer")
    public void listenAddBook(String message) {
        try {
            int bookId = Integer.parseInt(message);
            LocalDateTime now = LocalDateTime.now();

            BookInformation book = BookInformation.builder()
                    .bookId(bookId)
                    .status(true)
                    .receivedAt(now)
                    .timeToGiveBack(now.plusWeeks(AMOUNT_OF_WEEKS))
                    .deleted(false)
                    .build();

            bookInformationRepository.save(book);
        } catch (NumberFormatException e) {
            System.out.println("Invalid book ID received: " + message);
        }
    }

    /**
     * Listens to the "deleteBook" Kafka topic and marks a book as deleted.
     * @param message The book ID received as a string.
     */
    @KafkaListener(topics = "deleteBook", groupId = "first_consumer")
    public void listenDeleteBook(String message) {
        try {
            int id = Integer.parseInt(message);
            BookInformation book = bookInformationRepository.findById(id).orElse(null);
            if (book != null) {
                book.setDeleted(!book.getDeleted());
                bookInformationRepository.save(book);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid book ID received: " + message);
        }
    }
}
