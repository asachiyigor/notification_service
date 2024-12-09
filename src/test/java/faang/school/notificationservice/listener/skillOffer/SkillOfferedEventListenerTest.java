package faang.school.notificationservice.listener.skillOffer;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillOfferedEventListenerTest {
    @InjectMocks
    private SkillOfferedEventListener skillOfferedEventListener;

    @Mock
    private NotificationService notificationService;

    @Mock
    private MessageBuilder<SkillOfferedEvent> messageBuilder;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private Message message;

    private byte[] messageBody;


    @BeforeEach
    void setUp() {
        messageBody = new byte[100];
        List<MessageBuilder<SkillOfferedEvent>> messageBuilders = List.of(messageBuilder);
        List<NotificationService> notificationServices = List.of(notificationService);
        skillOfferedEventListener = new SkillOfferedEventListener(
                notificationServices,
                messageBuilders,
                objectMapper,
                userServiceClient);
        ReflectionTestUtils.setField(skillOfferedEventListener, "topic", "skillOffer-channel");
    }

    @Test
    void testGetChannelTopic_Positive() {
        ChannelTopic topic = skillOfferedEventListener.getChannelTopic();
        assertEquals("skillOffer-channel", topic.getTopic());
    }

    @Test
    void onMessage_ShouldHandleException() throws IOException {
        when(message.getBody()).thenReturn(messageBody);
        when(objectMapper.readValue(messageBody, SkillOfferedEvent.class)).thenThrow(new IOException());

        skillOfferedEventListener.onMessage(message, messageBody);

        verify(objectMapper, times(1)).readValue(messageBody, SkillOfferedEvent.class);
        verify(userServiceClient, never()).getUser(anyLong());
        verify(messageBuilder, never()).buildMessage(any(), any());
        verify(notificationService, never()).send(any(), any());

    }
}