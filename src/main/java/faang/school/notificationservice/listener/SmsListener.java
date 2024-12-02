package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.SmsDto;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class SmsListener extends AbstractListener<SmsDto> implements MessageListener {


    public SmsListener(ObjectMapper objectMapper, UserServiceClient userServiceClient,
                       List<NotificationService> notificationServices,
                       List<MessageBuilder<SmsDto>> messageBuilders) {
        super(objectMapper, userServiceClient, notificationServices, messageBuilders);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            SmsDto sms = objectMapper.readValue(message.getBody(), SmsDto.class);
            UserDto user = userServiceClient.getUser(sms.getUserId());
            String messageText = getMessage(sms, Locale.getDefault());
            sendNotification(user, messageText);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
