package by.baraznov.bookstorageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Book Get DTO")
public class GetBookDTO {
    @Schema(description = "book id", example = "1")
    private Integer id;
    @Schema(description = "book ISBN", example = "978-3-16-148410-0")
    private String isbn;
    @Schema(description = "book name", example = "Война и мир")
    private String name;
    @Schema(description = "book genre", example = "Роман")
    private String genre;
    @Schema(description = "book description", example = "Классический роман Льва Толстого")
    private String description;
    @Schema(description = "book author", example = "Лев Толстой")
    private String author;
}
