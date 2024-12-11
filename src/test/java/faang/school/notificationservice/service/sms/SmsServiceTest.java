package faang.school.notificationservice.service.sms;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import faang.school.notificationservice.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {
    @Mock
    VonageClient vonageClient;
    @Mock
    SmsSubmissionResponse response;
    @Mock
    SmsClient smsClient;

    @Test
    void positiveSend() {
        UserDto userDto = new UserDto();
        String jsonResponse = "{\"messages\": [{\"status\": \"0\"}]}";

        response = SmsSubmissionResponse.fromJson(jsonResponse);

        when(vonageClient.getSmsClient()).thenReturn(smsClient);
        when(smsClient.submitMessage(any(TextMessage.class))).thenReturn(response);

        SmsService smsService = new SmsService(vonageClient);

        smsService.send(userDto, "Hello");

        verify(smsClient, times(1)).submitMessage(any(TextMessage.class));
    }
}