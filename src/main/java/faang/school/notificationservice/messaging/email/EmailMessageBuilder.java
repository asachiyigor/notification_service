package faang.school.notificationservice.messaging.email;

import faang.school.notificationservice.dto.EmailEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EmailMessageBuilder implements MessageBuilder<EmailEvent> {
    private final MessageSource messageSource;

    @Override
    public String buildMessage(EmailEvent event, Locale locale) {
        return messageSource.getMessage(
                "email.new",
                new Object[]{event.getAuthorId(), event.getTimestamp(), event.getReceiverId()},
                locale
        );
    }

    @Override
    public Class<?> getInstance() {
        return EmailEvent.class;
    }
}
