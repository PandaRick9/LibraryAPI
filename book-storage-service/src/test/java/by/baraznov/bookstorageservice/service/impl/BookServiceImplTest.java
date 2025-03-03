package by.baraznov.bookstorageservice.service.impl;


import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.dto.UpdateBookDTO;
import by.baraznov.bookstorageservice.kafka.KafkaProducer;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;
import by.baraznov.bookstorageservice.mapper.book.UpdateBookMapper;
import by.baraznov.bookstorageservice.model.Book;
import by.baraznov.bookstorageservice.repository.BookRepository;
import by.baraznov.util.BookAlreadyExists;
import by.baraznov.util.BookNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GetBookMapper getBookMapper;

    @Mock
    private UpdateBookMapper updateBookMapper;

    @Mock
    private CreateBookMapper createBookMapper;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BookServiceImpl bookService;

    private CreateBookDTO createBookDTO;
    private UpdateBookDTO updateBookDTO;
    private GetBookDTO getBookDTO;
    private Book book;

    @BeforeEach
    public void setUp() {
        createBookDTO = new CreateBookDTO();
        updateBookDTO = new UpdateBookDTO();
        getBookDTO = new GetBookDTO();
        book = new Book();
        book.setDeleted(false);
    }

    @Test
    public void testGetAllBooks_Success() {
        when(bookRepository.findByDeletedFalse()).thenReturn(Collections.singletonList(book));
        when(getBookMapper.toDtos(anyList())).thenReturn(Collections.singletonList(getBookDTO));

        List<GetBookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(getBookDTO, result.get(0));
        verify(bookRepository, times(1)).findByDeletedFalse();
        verify(getBookMapper, times(1)).toDtos(anyList());
    }


    @Test
    public void testGetBookById_Success() {
        when(bookRepository.findByIdAndDeletedFalse(anyInt())).thenReturn(book);
        when(getBookMapper.toDto(any(Book.class))).thenReturn(getBookDTO);

        GetBookDTO result = bookService.getBookById(1);

        assertEquals(getBookDTO, result);
    }

    @Test
    public void testGetBookById_BookNotFound() {
        when(bookRepository.findByIdAndDeletedFalse(anyInt())).thenReturn(null);

        assertThrows(BookNotFound.class, () -> bookService.getBookById(1));

        verify(bookRepository, times(1)).findByIdAndDeletedFalse(anyInt());
        verify(getBookMapper, never()).toDto(any(Book.class));
    }

    @Test
    public void testGetBookByISBN_Success() {
        when(bookRepository.findByIsbnAndDeletedFalse(anyString())).thenReturn(book);
        when(getBookMapper.toDto(any(Book.class))).thenReturn(getBookDTO);

        GetBookDTO result = bookService.getBookByISBN("ISBN123");

        assertEquals(getBookDTO, result);
        verify(bookRepository, times(1)).findByIsbnAndDeletedFalse(anyString());
        verify(getBookMapper, times(1)).toDto(any(Book.class));
    }

    @Test
    public void testGetBookByISBN_BookNotFound() {
        when(bookRepository.findByIsbnAndDeletedFalse(anyString())).thenReturn(null);

        assertThrows(BookNotFound.class, () -> bookService.getBookByISBN("ISBN123"));

        verify(bookRepository, times(1)).findByIsbnAndDeletedFalse(anyString());
        verify(getBookMapper, never()).toDto(any(Book.class));
    }

    @Test
    public void testUpdateBook_Success() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(getBookMapper.toDto(any(Book.class))).thenReturn(getBookDTO);

        GetBookDTO result = bookService.update(1, updateBookDTO);

        assertEquals(getBookDTO, result);
        verify(bookRepository, times(1)).findById(anyInt());
        verify(updateBookMapper, times(1)).merge(any(Book.class), any(UpdateBookDTO.class));
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(getBookMapper, times(1)).toDto(any(Book.class));
    }

    @Test
    public void testUpdateBook_BookNotFound() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.update(1, updateBookDTO));

        verify(bookRepository, times(1)).findById(anyInt());
        verify(updateBookMapper, never()).merge(any(Book.class), any(UpdateBookDTO.class));
        verify(bookRepository, never()).save(any(Book.class));
        verify(getBookMapper, never()).toDto(any(Book.class));
    }

    @Test
    public void testDeleteBook_Success() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        bookService.delete(1);

        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testDeleteBook_BookNotFound() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.delete(1));

        verify(bookRepository, times(1)).findById(anyInt());
        verify(kafkaProducer, never()).sendDeleteMessage(anyInt());
        verify(bookRepository, never()).save(any(Book.class));
    }
}