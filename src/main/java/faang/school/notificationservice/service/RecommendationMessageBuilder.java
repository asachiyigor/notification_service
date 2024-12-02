package faang.school.notificationservice.service;

import faang.school.notificationservice.dto.RecommendationEvent;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RecommendationMessageBuilder implements MessageBuilder<RecommendationEvent> {
    private MessageSource messageSource;

    @Override
    public String buildMessage(RecommendationEvent event, Locale locale) {
        return messageSource.getMessage("notification.recommendation",
                new Object[]{event.getAuthorId(), event.getTimestamp()},
                locale);
    }

    @Override
    public Class<?> supportEventType() {
        return RecommendationEvent.class;
    }
}
