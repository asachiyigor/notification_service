package faang.school.notificationservice.listener.skillOffer;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SkillOfferedEventListener extends AbstractEventListener<SkillOfferedEvent> implements MessageListener {
    @Value("${spring.data.redis.channels.skillOffer-channel.name}")
    private String topic;

    public SkillOfferedEventListener(List<NotificationService> notificationServices,
                                     List<MessageBuilder<SkillOfferedEvent>> messageBuilders,
                                     ObjectMapper objectMapper,
                                     UserServiceClient userServiceClient) {
        super(notificationServices, messageBuilders, objectMapper, userServiceClient);
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(topic);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, SkillOfferedEvent.class, event ->{
            UserDto sender = userServiceClient.getUser(event.getSenderId());
            getMessage(SkillOfferedEvent.class, event, )
        });
    }
}
