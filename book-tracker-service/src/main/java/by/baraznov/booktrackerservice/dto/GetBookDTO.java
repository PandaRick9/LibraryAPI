package by.baraznov.booktrackerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for retrieving book information")
public class GetBookDTO {
    @Schema(description = "ID of the book", example = "1")
    private Integer bookId;

    @Schema(description = "Current status of the book (true - available, false - taken)", example = "true")
    private Boolean status;

    @Schema(description = "Timestamp when the book was received", example = "2024-02-08T12:00:00")
    private LocalDateTime receivedAt;

    @Schema(description = "Timestamp by which the book should be returned", example = "2024-03-08T12:00:00")
    private LocalDateTime timeToGiveBack;
}
