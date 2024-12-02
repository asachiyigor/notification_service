package faang.school.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecommendationEvent {
    private long authorId;
    private long receiverId;
    private String text;
    private LocalDateTime timestamp;
}
