package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.dto.event.ProjectFollowerEvent;
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
public class ProjectFollowerListener extends AbstractEventListener<ProjectFollowerEvent> implements MessageListener {
    private final UserServiceClient userServiceClient;

    @Value("${spring.data.redis.channel.project-follower-channel.name}")
    private String projectFollowerChannel;

    public ProjectFollowerListener(List<NotificationService> notificationServices,
                                   List<MessageBuilder<ProjectFollowerEvent>> messageBuilders,
                                   ObjectMapper objectMapper, UserServiceClient userServiceClient) {
        super(notificationServices, messageBuilders, objectMapper, userServiceClient);
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(projectFollowerChannel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, ProjectFollowerEvent.class, event -> {
            UserDto receiver = userServiceClient.getUser(event.ownerId());
            String text = getMessage(ProjectFollowerEvent.class, event, receiver.getLocale());
            sendNotification(receiver, text);
            log.info("Notification ProjectFollowerEvent was send.");
        });
    }
}
