package by.baraznov.bookstorageservice.db1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Schema(description = "book ISBN", example = "978-3-16-148410-0")
    private String isbn;
    @Column(name = "name")
    @Schema(description = "book name", example = "Война и мир")
    private String name;
    @Column(name = "genre")
    @Schema(description = "book genre", example = "Роман")
    private String genre;
    @Column(name = "description")
    @Schema(description = "book description", example = "Классический роман Льва Толстого")
    private String description;
    @Column(name = "author")
    @Schema(description = "book author", example = "Лев Толстой")
    private String author;
    @Column(name = "deleted")
    @Schema(description = "flag that shows whether the book has been deleted", example = "false")
    private Boolean deleted;
}

