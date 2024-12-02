package faang.school.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EmailEvent {
    private long receiverId;
    private long authorId;
    private LocalDateTime timestamp;
}
