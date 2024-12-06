package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractEventListenerTest {
    @Mock
    private NotificationService notificationService;

    @Mock
    private MessageBuilder<Event> messageBuilder;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private Message message;

    private AbstractEventListener<Event> abstractEventListener;
    private Event event;
    private byte[] messageBody;
    private Consumer<Event> consumer;
    private String messageBuild;
    private UserDto userDto;
    private String channelTopic;

    @BeforeEach
    void setUp() {
        messageBody = new byte[100];
        event = new Event("TestEvent");
        messageBuild = "Test message";
        consumer = System.out::println;
        channelTopic = "test-topic";
        userDto = UserDto.builder()
                .id(1L)
                .email("user@gmail.com")
                .preference(UserDto.PreferredContact.EMAIL)
                .build();
        abstractEventListener = new AbstractEventListener<>(
                List.of(notificationService),
                List.of(messageBuilder),
                objectMapper,
                userServiceClient
        ) {
            @Override
            public ChannelTopic getChannelTopic() {
                return new ChannelTopic(channelTopic);
            }
        };
    }

    @Test
    void testHandleEvent_Positive() throws IOException {
        when(message.getBody()).thenReturn(messageBody);
        when(objectMapper.readValue(messageBody, Event.class)).thenReturn(event);
        abstractEventListener.handleEvent(message, Event.class, consumer);
        verify(objectMapper, times(1)).readValue(messageBody, Event.class);
    }

    @Test
    void testHandleEvent_Negative() throws IOException {
        when(message.getBody()).thenReturn(messageBody);
        when(objectMapper.readValue(messageBody, Event.class)).thenThrow(IOException.class);

        assertThrows(RuntimeException.class, () -> abstractEventListener.handleEvent(message, Event.class, consumer));
    }

    @Test
    void testGetMessage_Negative() {
        assertThrows(IllegalArgumentException.class, () ->
                abstractEventListener.getMessage(Event.class, event, Locale.ENGLISH));
    }

    @Test
    void testSendNotification_Positive() {
        when(notificationService.getPreferredContact()).thenReturn(UserDto.PreferredContact.EMAIL);
        abstractEventListener.sendNotification(userDto, "Test message");
        verify(notificationService, times(1)).send(userDto, messageBuild);
    }

    @Test
    void testSendNotification_Negative() {
        when(notificationService.getPreferredContact()).thenReturn(UserDto.PreferredContact.SMS);
        assertThrows(IllegalArgumentException.class, () ->
                abstractEventListener.sendNotification(userDto, "Test message"));
    }

    @Data
    static class Event {
        private String name;
        Event(String name) {
            this.name = name;
        }
    }
}