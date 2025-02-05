package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.service.impl.BookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping
    public List<GetBookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public GetBookDTO create(@RequestBody CreateBookDTO createBookDTO) {
        return bookService.create(createBookDTO);
    }

    @GetMapping("/id/{id}")
    public GetBookDTO getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/isbn/{isbn}")
    public GetBookDTO getBookByISBN(@PathVariable String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @PatchMapping("/{id}")
    public GetBookDTO update(@PathVariable int id, @RequestBody CreateBookDTO createBookDTO) {
        return bookService.update(id, createBookDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookService.delete(id);
    }


}
