package faang.school.notificationservice.dto.event;

import lombok.Builder;

@Builder
public record UserProfileEvent(
        long viewerId,
        long viewedId
) {
}
