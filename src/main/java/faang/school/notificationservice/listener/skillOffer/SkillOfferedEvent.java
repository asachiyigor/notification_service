package faang.school.notificationservice.listener.skillOffer;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillOfferedEvent {
    @Positive
    private long receiverId;
    @Positive
    private long senderId;
    @Positive
    private long skillId;
}
