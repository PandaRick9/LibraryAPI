package by.baraznov.bookstorageservice.service;


import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BookService {
    List<GetBookDTO> getAllBooks();
    GetBookDTO create(CreateBookDTO createBookDTO);
    GetBookDTO getBookById(int id);
    GetBookDTO getBookByISBN(String ISBN);
    GetBookDTO update(int id, CreateBookDTO createBookDTO);
    void delete(int id);
}
