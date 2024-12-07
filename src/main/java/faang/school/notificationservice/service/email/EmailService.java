package faang.school.notificationservice.service.email;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;
import faang.school.notificationservice.validator.email.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements NotificationService {
    private final JavaMailSender mailSender;
    private final EmailValidator emailValidator;

    @Value("{spring.mail.username}")
    private String serviceMail;

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.EMAIL;
    }

    @Override
    public void send(UserDto user, String text) {
        emailValidator.checkUserEmailAddress(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(serviceMail);
        message.setTo(user.getEmail());
        message.setSubject("Notification from Notification Service");
        message.setText(text);
        mailSender.send(message);
    }


}
