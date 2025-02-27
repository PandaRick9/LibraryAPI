package by.baraznov.booktrackerservice.controller;

import by.baraznov.booktrackerservice.model.BookInformation;
import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.book.GetBookMapper;
import by.baraznov.booktrackerservice.service.impl.BookInformationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookInformationServiceImpl bookInformationService;

    @Mock
    private GetBookMapper getBookMapper;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllFreeBooks_ShouldReturnFreeBooks() throws Exception {
        GetBookDTO getBookDTO = new GetBookDTO();
        getBookDTO.setBookId(1);
        getBookDTO.setStatus(true);

        when(bookInformationService.findAllFreeBooks()).thenReturn(Collections.emptyList());
        when(getBookMapper.toDtos(anyList())).thenReturn(Collections.singletonList(getBookDTO));

        mockMvc.perform(get("/tracker"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].status").value(true));

        verify(bookInformationService, times(1)).findAllFreeBooks();
        verify(getBookMapper, times(1)).toDtos(anyList());
    }

    @Test
    void setNewStatus_ShouldReturnUpdatedBook() throws Exception {
        int bookId = 1;
        GetBookDTO getBookDTO = new GetBookDTO();
        getBookDTO.setBookId(bookId);
        getBookDTO.setStatus(false);

        when(bookInformationService.findOne(bookId)).thenReturn(new BookInformation());
        when(getBookMapper.toDto(any(BookInformation.class))).thenReturn(getBookDTO);

        mockMvc.perform(post("/tracker/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.status").value(false));

        verify(bookInformationService, times(1)).changeStatus(bookId);
        verify(bookInformationService, times(1)).findOne(bookId);
        verify(getBookMapper, times(1)).toDto(any(BookInformation.class));
    }

}