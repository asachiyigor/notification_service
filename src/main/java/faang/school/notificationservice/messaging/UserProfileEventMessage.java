package faang.school.notificationservice.messaging;

import faang.school.notificationservice.dto.event.UserProfileEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
@RequiredArgsConstructor
public class UserProfileEventMessage implements MessageBuilder<UserProfileEvent>{
    private final MessageSource messageSource;
    @Override
    public Class<?> getInstance() {
        return UserProfileEvent.class;
    }

    @Override
    public String buildMessage(UserProfileEvent event, Locale locale) {
        return messageSource.getMessage("viewed.new", new Object[]{event.viewerId()},locale);
    }
}
