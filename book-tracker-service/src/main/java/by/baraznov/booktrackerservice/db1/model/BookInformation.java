package by.baraznov.booktrackerservice.db1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "book_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Book Information Entity")
public class BookInformation {
    @Id
    @Column(name = "book_id")
    @Schema(description = "ID of the book", example = "1")
    private Integer bookId;

    @Column(name = "status")
    @Schema(description = "Indicates whether the book is available (true - available, false - taken)", example = "true")
    private Boolean status;

    @Column(name = "received_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Timestamp when the book was received", example = "2024-02-08T12:00:00")
    private LocalDateTime receivedAt;

    @Column(name = "time_to_give_back")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Timestamp by which the book should be returned", example = "2024-03-08T12:00:00")
    private LocalDateTime timeToGiveBack;

    @Column(name = "deleted")
    @Schema(description = "Flag that shows whether the book information has been deleted", example = "false")
    private Boolean deleted;
}
