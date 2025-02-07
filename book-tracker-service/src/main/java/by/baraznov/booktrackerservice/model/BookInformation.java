package by.baraznov.booktrackerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
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
public class BookInformation {
    @Id
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "status")
    private Boolean free;
    @Column(name = "received_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime receivedAt;
    @Column(name = "time_to_give_back")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime  timeToGiveBack;
    @Column(name = "deleted")
    private Boolean deleted;
}
