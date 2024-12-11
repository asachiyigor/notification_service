package faang.school.notificationservice.service.sms;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService implements NotificationService {

    private final VonageClient vonageClient;

    @Override
    public void send(UserDto user, String messageText) {
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            throw new IllegalArgumentException("Phone can`t be blank");
        }

        TextMessage textMessage = new TextMessage("Vii`Rus",
                user.getPhone(),
                messageText
        );

        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(textMessage);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            log.info("Message sent successfully.");
        } else {
            log.error("Message failed with error: {}", response.getMessages().get(0).getErrorText());
        }
    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.SMS;
    }
}
