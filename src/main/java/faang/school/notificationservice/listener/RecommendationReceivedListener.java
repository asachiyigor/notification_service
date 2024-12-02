package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.RecommendationEvent;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class RecommendationReceivedListener extends AbstractEventListener<RecommendationEvent> implements MessageListener {
    public RecommendationReceivedListener(ObjectMapper objectMapper,
                                          UserServiceClient userServiceClient,
                                          List<NotificationService> notificationServices,
                                          List<MessageBuilder<RecommendationEvent>> messageBuilders) {
        super(objectMapper, userServiceClient, notificationServices, messageBuilders);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, RecommendationEvent.class, event -> {
            UserDto author = userServiceClient.getUser(event.getReceiverId());
            String text = getMessage(event, Locale.UK);
            sendNotification(event.getReceiverId(), text);
        });
    }
}
