package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.dto.UpdateBookDTO;
import by.baraznov.bookstorageservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks_ShouldReturnBooks() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    void create_ShouldReturnCreatedBook() throws Exception {
        GetBookDTO getBookDTO = new GetBookDTO();
        when(bookService.create(any(CreateBookDTO.class))).thenReturn(getBookDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookById_ShouldReturnBook() throws Exception {
        GetBookDTO getBookDTO = new GetBookDTO();
        when(bookService.getBookById(anyInt())).thenReturn(getBookDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(get("/books/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookByISBN_ShouldReturnBook() throws Exception {
        GetBookDTO getBookDTO = new GetBookDTO();
        when(bookService.getBookByISBN(anyString())).thenReturn(getBookDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(get("/books/isbn/1234567890"))
                .andExpect(status().isOk());
    }

    @Test
    void update_ShouldReturnUpdatedBook() throws Exception {
        GetBookDTO getBookDTO = new GetBookDTO();
        when(bookService.update(anyInt(), any(UpdateBookDTO.class))).thenReturn(getBookDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(patch("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(bookService).delete(anyInt());

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());
    }
}