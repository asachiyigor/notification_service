package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.event.ProjectFollowerEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Locale;

@Component
public class ProjectFollowerListener extends AbstractEventListener<ProjectFollowerEvent> implements MessageListener {
    public ProjectFollowerListener(List<NotificationService> notificationServices, List<MessageBuilder<ProjectFollowerEvent>> messageBuilders, ObjectMapper objectMapper, UserServiceClient userServiceClient) {
        super(notificationServices, messageBuilders, objectMapper, userServiceClient);
    }

    @Value("${spring.data.redis.channel.project-follower-channel.name}")
    private String projectFollowerChannel;

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(projectFollowerChannel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message,ProjectFollowerEvent.class, event ->{
            String text = getMessage(ProjectFollowerEvent.class,event,Locale.getDefault());
            sendNotification(event.ownerId(), );
        });
    }
}
