package by.baraznov.booktrackerservice.dto;

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
public class GetBookDTO {
    private Integer bookId;
    private Boolean status;
    private LocalDateTime  receivedAt;
    private LocalDateTime timeToGiveBack;
}
