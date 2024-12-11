package faang.school.notificationservice.messaging.skillOffer;

import faang.school.notificationservice.listener.skillOffer.SkillOfferedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
class SkillOfferMessageTest {

    @InjectMocks
    private SkillOfferMessage skillOfferMessage;

    @Mock
    private MessageSource messageSource;


    @Test
    void buildMessage() {
        String message = "User 1 is offering skill 1";
        SkillOfferedEvent event = new SkillOfferedEvent(1L, 1L, 1L);

        when(messageSource.getMessage(anyString(), any(), any())).thenReturn(message);

        String result = skillOfferMessage.buildMessage(event, Locale.getDefault());
        assertEquals(message, result);
    }
}