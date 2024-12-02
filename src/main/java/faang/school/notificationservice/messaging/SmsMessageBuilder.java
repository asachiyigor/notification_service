package faang.school.notificationservice.messaging;

import faang.school.notificationservice.dto.SmsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SmsMessageBuilder implements MessageBuilder<SmsDto> {

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return SmsDto.class;
    }

    @Override
    public String buildMessage(SmsDto event, Locale locale) {
        return messageSource.getMessage("follower.new", null, locale);
    }
}
