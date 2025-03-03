package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.dto.UpdateBookDTO;
import by.baraznov.bookstorageservice.service.impl.BookServiceImpl;
import by.baraznov.util.BookAlreadyExists;
import by.baraznov.util.BookNotFound;
import by.baraznov.util.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BookController bookController;

    private CreateBookDTO createBookDTO;
    private UpdateBookDTO updateBookDTO;
    private GetBookDTO getBookDTO;

    @BeforeEach
    public void setUp() {
        createBookDTO = new CreateBookDTO();
        updateBookDTO = new UpdateBookDTO();
        getBookDTO = new GetBookDTO();
    }

    @Test
    public void testGetAllBooks_Success() {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(getBookDTO));

        ResponseEntity<List<GetBookDTO>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(getBookDTO, response.getBody().get(0));
    }

    @Test
    public void testCreateBook_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.create(any(CreateBookDTO.class))).thenReturn(getBookDTO);

        ResponseEntity<?> response = bookController.create(createBookDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getBookDTO, response.getBody());
    }

    @Test
    public void testCreateBook_BindingResultError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("createBookDTO", "title", "Title is required");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<?> response = bookController.create(createBookDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("title - Title is required;", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testCreateBook_BookAlreadyExists() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.create(any(CreateBookDTO.class))).thenThrow(new BookAlreadyExists("Book already exists"));

        ResponseEntity<?> response = bookController.create(createBookDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Book already exists", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testCreateBook_InternalServerError() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.create(any(CreateBookDTO.class))).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = bookController.create(createBookDTO, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testGetBookById_Success() throws BookNotFound {
        when(bookService.getBookById(1)).thenReturn(getBookDTO);

        ResponseEntity<?> response = bookController.getBookById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getBookDTO, response.getBody());
    }

    @Test
    public void testGetBookById_BookNotFound() throws BookNotFound {
        when(bookService.getBookById(1)).thenThrow(new BookNotFound("Book not found"));

        ResponseEntity<?> response = bookController.getBookById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testGetBookById_InternalServerError() throws BookNotFound {
        when(bookService.getBookById(1)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = bookController.getBookById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testGetBookByISBN_Success() throws BookNotFound {
        when(bookService.getBookByISBN("ISBN123")).thenReturn(getBookDTO);

        ResponseEntity<?> response = bookController.getBookByISBN("ISBN123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getBookDTO, response.getBody());
    }

    @Test
    public void testGetBookByISBN_BookNotFound() throws BookNotFound {
        when(bookService.getBookByISBN("ISBN123")).thenThrow(new BookNotFound("Book not found"));

        ResponseEntity<?> response = bookController.getBookByISBN("ISBN123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testGetBookByISBN_InternalServerError() throws BookNotFound {
        when(bookService.getBookByISBN("ISBN123")).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = bookController.getBookByISBN("ISBN123");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testUpdateBook_Success() throws BookNotFound {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.update(1, updateBookDTO)).thenReturn(getBookDTO);

        ResponseEntity<?> response = bookController.update(1, updateBookDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getBookDTO, response.getBody());
    }

    @Test
    public void testUpdateBook_BindingResultError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("updateBookDTO", "title", "Title is required");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<?> response = bookController.update(1, updateBookDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("title - Title is required;", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testUpdateBook_BookNotFound() throws BookNotFound {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.update(1, updateBookDTO)).thenThrow(new BookNotFound("Book not found"));

        ResponseEntity<?> response = bookController.update(1, updateBookDTO, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testUpdateBook_InternalServerError() throws BookNotFound {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.update(1, updateBookDTO)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = bookController.update(1, updateBookDTO, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testDeleteBook_Success() throws BookNotFound {
        doNothing().when(bookService).delete(1);

        ResponseEntity<?> response = bookController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteBook_BookNotFound() throws BookNotFound {
        doThrow(new BookNotFound("Book not found")).when(bookService).delete(1);

        ResponseEntity<?> response = bookController.delete(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testDeleteBook_InternalServerError() throws BookNotFound {
        doThrow(new RuntimeException("Unexpected error")).when(bookService).delete(1);

        ResponseEntity<?> response = bookController.delete(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }
}