package faang.school.notificationservice.service;

import faang.school.notificationservice.dto.FollowerEvent;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FollowerMessageBuilder implements MessageBuilder<FollowerEvent> {
    private MessageSource messageSource;

    @Override
    public String buildMessage(FollowerEvent event, Locale locale) {
//        загуглить как работает messageSource и настроить
        return messageSource.getMessage(
                "notification.follower",
                new Object[]{event.getFollowerId(), event.getTimestamp()},
                locale);
    }

    @Override
    public Class<?> supportEventType() {
        return FollowerEvent.class;
    }
}
