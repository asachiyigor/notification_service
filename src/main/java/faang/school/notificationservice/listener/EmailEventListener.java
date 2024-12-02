package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.EmailEvent;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmailEventListener extends AbstractEventListener<EmailEvent> implements MessageListener {

    public EmailEventListener(ObjectMapper objectMapper,
                              UserServiceClient userServiceClient,
                              List<NotificationService> notificationServices,
                              List<MessageBuilder<EmailEvent>> messageBuilders) {
        super(objectMapper, userServiceClient, notificationServices, messageBuilders);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, EmailEvent.class, event ->{
            UserDto author = userServiceClient.getUser(event.getAuthorId());
            String text = getMessage(event, author.getLocale());
            sendNotification(event.getReceiverId(), text);
            log.info("Notification was sent to userId={}, text: {}", event.getReceiverId(), text);
        });
    }
}
