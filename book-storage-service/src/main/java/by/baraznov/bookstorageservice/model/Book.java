package by.baraznov.bookstorageservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Book Entity")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "book id", example = "1")
    private Integer id;
    @Column(name = "ISBN")
    @NotNull(message = "ISBN cannot be null")
    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{1,7}-\\d{1,6}-\\d$", message = "Invalid ISBN format, example = 978-3-16458-543-0")
    @Schema(description = "book ISBN", example = "978-3-16458-543-0")
    private String isbn;
    @Column(name = "name")
    @NotBlank(message = "Book name cannot be blank")
    @Size(min = 2, max = 50, message = "Book name must be between 2 and 50 characters")
    @Schema(description = "book name", example = "Война и мир")
    private String name;
    @Column(name = "genre")
    @NotBlank(message = "Genre cannot be blank")
    @Size(min = 2, max = 50, message = "Genre must be between 1 and 50 characters")
    @Schema(description = "book genre", example = "Роман")
    private String genre;
    @Column(name = "description")
    @Size(max = 200, message = "Description must be no longer than 200 characters")
    @Schema(description = "book description", example = "Классический роман Льва Толстого")
    private String description;
    @Column(name = "author")
    @NotBlank(message = "Author cannot be blank")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters")
    @Schema(description = "book author", example = "Лев Толстой")
    private String author;
    @Column(name = "deleted")
    @Schema(description = "flag that shows whether the book has been deleted", example = "false")
    private Boolean deleted;
}

