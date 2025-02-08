package by.baraznov.booktrackerservice.controller;

import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.book.GetBookMapper;
import by.baraznov.booktrackerservice.service.impl.BookInformationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
    public List<GetBookDTO> getAllFreeBooks() {
        return getBookMapper.toDtos(bookInformationService.findAllFreeBooks());
    }

    @Operation(
            summary = "Update book status",
            description = "Changes the status of a book by its ID and returns the updated book information."
    )
    @PostMapping("/{id}")
    public GetBookDTO setNewStatus(@PathVariable int id) {
        bookInformationService.changeStatus(id);
        return getBookMapper.toDto(bookInformationService.findOne(id));
    }

}
