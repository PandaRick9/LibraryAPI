package by.baraznov.bookstorageservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {
    private String ISBN;
    private String name;
    private String genre;
    private String description;
    private String author;
}
