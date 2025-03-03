package by.baraznov.bookstorageservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Book Update DTO")
public class UpdateBookDTO {
    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d$", message = "Invalid ISBN format, example = 978-3-16458-543-0")
    @Schema(description = "book ISBN", example = "978-3-16-148410-0")
    private String isbn;
    @Size(min = 2, max = 50, message = "Book name must be between 2 and 50 characters")
    @Schema(description = "book name", example = "Война и мир")
    private String name;
    @Size(min = 2, max = 50, message = "Genre must be between 1 and 50 characters")
    @Schema(description = "book genre", example = "Роман")
    private String genre;
    @Size(max = 200, message = "Description must be no longer than 200 characters")
    @Schema(description = "book description", example = "Классический роман Льва Толстого")
    private String description;
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    @Schema(description = "book author", example = "Лев Толстой")
    private String author;
}
