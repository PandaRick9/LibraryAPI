package by.baraznov.booktrackerservice.kafka;

import by.baraznov.booktrackerservice.model.BookInformation;
import by.baraznov.booktrackerservice.repository.BookInformationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest {

    @Mock
    private BookInformationRepository bookInformationRepository;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    private final long AMOUNT_OF_WEEKS = 1L;

    @Test
    void listenAddBook_ValidBookId_ShouldSaveBook() {
        String validBookId = "123";
        int bookId = Integer.parseInt(validBookId);
        LocalDateTime now = LocalDateTime.now();

        BookInformation expectedBook = BookInformation.builder()
                .bookId(bookId)
                .status(true)
                .receivedAt(now)
                .timeToGiveBack(now.plusWeeks(AMOUNT_OF_WEEKS))
                .deleted(false)
                .build();

        kafkaConsumer.listenAddBook(validBookId);
    }

    @Test
    void listenAddBook_InvalidBookId_ShouldNotSaveBook() {
        String invalidBookId = "invalidId";

        kafkaConsumer.listenAddBook(invalidBookId);

        verify(bookInformationRepository, never()).save(any(BookInformation.class));
    }

    @Test
    void listenDeleteBook_ValidBookId_ShouldToggleDeletedStatus() {
        String validBookId = "123";
        int bookId = Integer.parseInt(validBookId);

        BookInformation existingBook = BookInformation.builder()
                .bookId(bookId)
                .status(true)
                .receivedAt(LocalDateTime.now())
                .timeToGiveBack(LocalDateTime.now().plusWeeks(AMOUNT_OF_WEEKS))
                .deleted(false)
                .build();

        when(bookInformationRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        kafkaConsumer.listenDeleteBook(validBookId);

        verify(bookInformationRepository, times(1)).findById(bookId);
        verify(bookInformationRepository, times(1)).save(existingBook);
        assertTrue(existingBook.getDeleted());
    }

    @Test
    void listenDeleteBook_InvalidBookId_ShouldNotUpdateBook() {
        String invalidBookId = "invalidId";

        kafkaConsumer.listenDeleteBook(invalidBookId);

        verify(bookInformationRepository, never()).findById(anyInt());
        verify(bookInformationRepository, never()).save(any(BookInformation.class));
    }

    @Test
    void listenDeleteBook_NonExistentBookId_ShouldNotUpdateBook() {
        String validBookId = "123";
        int bookId = Integer.parseInt(validBookId);

        when(bookInformationRepository.findById(bookId)).thenReturn(Optional.empty());

        kafkaConsumer.listenDeleteBook(validBookId);

        verify(bookInformationRepository, times(1)).findById(bookId);
        verify(bookInformationRepository, never()).save(any(BookInformation.class));
    }
}