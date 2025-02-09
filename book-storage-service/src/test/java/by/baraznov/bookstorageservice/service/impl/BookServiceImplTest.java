package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.db1.model.Book;
import by.baraznov.bookstorageservice.db1.repository.BookRepository;
import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.kafka.KafkaProducer;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private GetBookMapper getBookMapper;
    @Mock
    private CreateBookMapper createBookMapper;
    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private GetBookDTO getBookDTO;
    private CreateBookDTO createBookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1);
        book.setIsbn("12345");
        book.setDeleted(false);

        getBookDTO = new GetBookDTO();
        createBookDTO = new CreateBookDTO();
    }

    @Test
    void getAllBooks_ShouldReturnBookList() {
        when(bookRepository.findByDeletedFalse()).thenReturn(List.of(book));
        when(getBookMapper.toDtos(any())).thenReturn(List.of(getBookDTO));
        List<GetBookDTO> books = bookService.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    void create_ShouldCreateBookAndSendMessage() {
        when(createBookMapper.toEntity(createBookDTO)).thenReturn(book);
        when(bookRepository.save(any())).thenReturn(book);
        when(getBookMapper.toDto(any())).thenReturn(getBookDTO);

        GetBookDTO createdBook = bookService.create(createBookDTO);

        assertNotNull(createdBook);
    }

    @Test
    void getBookById_ShouldReturnBookDTO() {
        when(bookRepository.findByIdAndDeletedFalse(1)).thenReturn(book);
        when(getBookMapper.toDto(book)).thenReturn(getBookDTO);
        GetBookDTO result = bookService.getBookById(1);
        assertNotNull(result);
    }

    @Test
    void getBookByISBN_ShouldReturnBookDTO() {
        when(bookRepository.findByIsbnAndDeletedFalse("12345")).thenReturn(book);
        when(getBookMapper.toDto(book)).thenReturn(getBookDTO);
        GetBookDTO result = bookService.getBookByISBN("12345");
        assertNotNull(result);
    }

    @Test
    void update_ShouldUpdateBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(getBookMapper.toDto(book)).thenReturn(getBookDTO);
        GetBookDTO result = bookService.update(1, createBookDTO);
        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void delete_ShouldToggleDeletedStatusAndSendMessage() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        bookService.delete(1);
        assertTrue(book.getDeleted());
        verify(bookRepository, times(1)).save(book);
        verify(kafkaProducer, times(1)).sendDeleteMessage(1);
    }
}
