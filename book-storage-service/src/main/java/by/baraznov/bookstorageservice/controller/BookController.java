package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "book_method")
@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;
    @Operation(
         summary = "Getting all the books",
            description = "Getting all books from the repository and sending them as a response"
    )
    @GetMapping
    public List<GetBookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }
    @Operation(
            summary = "Creating a new book",
            description = "Receiving a DTO with information and sending to the database"
    )
    @PostMapping
    public GetBookDTO create(@RequestBody CreateBookDTO createBookDTO) {
        return bookService.create(createBookDTO);
    }
    @Operation(
            summary = "Getting the book by id",
            description = "Getting book by id from the repository and sending them as a response"
    )
    @GetMapping("/id/{id}")
    public GetBookDTO getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    @Operation(
            summary = "Getting the book by ISBN",
            description = "Getting book by ISBN from the repository and sending them as a response"
    )
    @GetMapping("/isbn/{isbn}")
    public GetBookDTO getBookByISBN(@PathVariable String isbn) {
        return bookService.getBookByISBN(isbn);
    }
    @Operation(
            summary = "Update info about book",
            description = "Receiving id and DTO and update info about book. After that updating info  in database"
    )
    @PatchMapping("/{id}")
    public GetBookDTO update(@PathVariable int id, @RequestBody CreateBookDTO createBookDTO) {
        return bookService.update(id, createBookDTO);
    }
    @Operation(
            summary = "Deleting book",
            description = "When deleting the book is not deleted from the database" +
                    " but a flag is set that the book was deleted so as not to lose data"
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookService.delete(id);
    }


}
