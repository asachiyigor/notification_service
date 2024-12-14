package faang.school.notificationservice.dto.event;

import lombok.Builder;

@Builder
public record ProjectFollowerEvent(
        long followerId,
        long projectId,
        long ownerId
) {
}
