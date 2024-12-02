package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.FollowerEvent;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEvent>  implements MessageListener {
    public FollowerEventListener(ObjectMapper objectMapper,
                                 UserServiceClient userServiceClient,
                                 List<NotificationService> list,
                                 List<MessageBuilder<FollowerEvent>> messageBuilders) {
        super(objectMapper, userServiceClient, list, messageBuilders);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, FollowerEvent.class, event ->{
            UserDto follower = userServiceClient.getUser(event.getFollowerId());
            String text = getMessage(event, follower.getLocale());
            sendNotification(event.getFolloweeId(), text);
        });
    }
}
