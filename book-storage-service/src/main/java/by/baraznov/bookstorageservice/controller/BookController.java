package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.service.impl.BookServiceImpl;
import by.baraznov.util.BookAlreadyExists;
import by.baraznov.util.BookNotFound;
import by.baraznov.util.ErrorResponse;
import by.baraznov.util.InvalidDataException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<GetBookDTO>> getAllBooks() {
        List<GetBookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(
            summary = "Creating a new book",
            description = "Receiving a DTO with information and sending to the database"
    )
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateBookDTO createBookDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            ErrorResponse errorResponse = new ErrorResponse(errorMsg.toString(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            GetBookDTO book = bookService.create(createBookDTO);
            return ResponseEntity.ok(book);
        }catch (BookAlreadyExists ex){
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(
            summary = "Getting the book by id",
            description = "Getting book by id from the repository and sending them as a response"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            GetBookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (BookNotFound ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Getting the book by ISBN",
            description = "Getting book by ISBN from the repository and sending them as a response"
    )
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByISBN(@PathVariable String isbn) {
        try {
            GetBookDTO book = bookService.getBookByISBN(isbn);
            return ResponseEntity.ok(book);
        } catch (BookNotFound ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(
            summary = "Update info about book",
            description = "Receiving id and DTO and update info about book. After that updating info  in database"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CreateBookDTO createBookDTO) {
        try {
            GetBookDTO updatedBook = bookService.update(id, createBookDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (BookNotFound ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Deleting book",
            description = "When deleting the book is not deleted from the database" +
                    " but a flag is set that the book was deleted so as not to lose data"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (BookNotFound ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
