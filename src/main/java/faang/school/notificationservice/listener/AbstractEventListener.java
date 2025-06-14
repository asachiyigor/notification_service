package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {

    private final List<NotificationService> notificationServices;
    private final List<MessageBuilder<T>> messageBuilders;
    private final ObjectMapper objectMapper;
    protected final UserServiceClient userServiceClient;

    protected void handleEvent(Message message, Class<T> eventClass, Consumer<T> consumer) {
        try {
            T event = objectMapper.readValue(message.getBody(), eventClass);
            log.info("Received event: {}", event);
            consumer.accept(event);
        } catch (IOException e) {
            log.error("Failed to handle event", e);
        }
    }

    protected String getMessage(Class<?> eventClass, T event, Locale userlocale) {
        String text = messageBuilders.stream()
                .filter(builder -> builder.getInstance() == eventClass)
                .findFirst()
                .map(messageBuilder -> messageBuilder.buildMessage(event, userlocale))
                .orElseThrow(
                        () -> new IllegalArgumentException("No message builder found for " + eventClass.getName())
                );
        log.info("Message built for event {}: {}",eventClass.getName(), text);
        return text;
    }

    protected void sendNotification(UserDto receiver, String message) {
        notificationServices.stream()
                .filter(service -> service.getPreferredContact() == receiver.getPreference())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("No notification service found for receiver " + receiver.getEmail()))
                .send(receiver, message);
        log.info("Notification sent to receiver {} with message: {}", receiver.getEmail(), message);
    }

    public MessageListenerAdapter getListenerAdapter() {
        return new MessageListenerAdapter(this);
    }

    public abstract ChannelTopic getChannelTopic();
}
