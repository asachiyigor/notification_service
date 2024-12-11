package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.dto.event.UserProfileEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserProfileViewListener extends AbstractEventListener<UserProfileEvent> implements MessageListener {
    private final UserServiceClient userServiceClient;
    @Value("${spring.data.redis.channels.user-view-channel.name}")
    private String userProfileEventChannel;

    public UserProfileViewListener(List<NotificationService> notificationServices,
                                   List<MessageBuilder<UserProfileEvent>> messageBuilders,
                                   ObjectMapper objectMapper,
                                   UserServiceClient userServiceClient) {
        super(notificationServices, messageBuilders, objectMapper, userServiceClient);
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(userProfileEventChannel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, UserProfileEvent.class, event -> {
            UserDto viewedUser = userServiceClient.getUser(event.viewedId());
            String text = getMessage(UserProfileEvent.class, event, viewedUser.getLocale());
            sendNotification(viewedUser, text);
            log.info("Notification UserProfileEvent was send.");
        });
    }
}
