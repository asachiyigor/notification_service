package faang.school.notificationservice.messaging.skillOffer;

import faang.school.notificationservice.listener.skillOffer.SkillOfferedEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SkillOfferMessage implements MessageBuilder<SkillOfferedEvent> {
    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return SkillOfferedEvent.class;
    }

    @Override
    public String buildMessage(SkillOfferedEvent event, Locale locale) {
        return messageSource.getMessage(
                "skillOffered",
                new Object[]{event.getSenderId(), event.getSkillId()},
                locale);
    }
}
