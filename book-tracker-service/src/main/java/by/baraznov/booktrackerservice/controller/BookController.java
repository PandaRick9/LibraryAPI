package by.baraznov.booktrackerservice.controller;

import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.book.GetBookMapper;
import by.baraznov.booktrackerservice.service.impl.BookInformationServiceImpl;
import by.baraznov.booktrackerservice.util.BookNotFound;
import by.baraznov.booktrackerservice.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "bookInfo_method")
@RestController
@RequestMapping("/tracker")
@AllArgsConstructor
public class BookController {
    private final BookInformationServiceImpl bookInformationService;
    private final GetBookMapper getBookMapper;

    @Operation(
            summary = "Getting all free books",
            description = "Retrieves all books that are currently available (not taken) from the repository."
    )
    @GetMapping
    public ResponseEntity<List<GetBookDTO>> getAllFreeBooks() {
        return ResponseEntity.ok(getBookMapper.toDtos(bookInformationService.findAllFreeBooks()));
    }

    @Operation(
            summary = "Update book status",
            description = "Changes the status of a book by its ID and returns the updated book information."
    )
    @PostMapping("/{id}")
    public ResponseEntity<?> setNewStatus(@PathVariable int id) {
        try {
            bookInformationService.changeStatus(id);
            return ResponseEntity.ok(getBookMapper.toDto(bookInformationService.findOne(id)));
        } catch (BookNotFound ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
