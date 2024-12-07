package faang.school.notificationservice.service.email;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.validator.mail.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailValidator emailValidator;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    private UserDto userDto;
    private SimpleMailMessage message;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .email("username@gmail.com")
                .build();
        message = new SimpleMailMessage();
        message.setTo(userDto.getEmail());
        message.setSubject("Notification from Notification Service");
        message.setText("Test");
    }

    @Test
    void testSend_Positive() {
        doNothing().when(emailValidator).checkUserEmailAddress(userDto);
        emailService.send(userDto, "Test");
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage result = messageCaptor.getValue();
        assertEquals(result, message);
    }

    @Test
    void testSend_emailIsNull_Negative() {
        userDto.setEmail(null);
        doThrow(new IllegalArgumentException("Not found email for user with id=1"))
                .when(emailValidator)
                .checkUserEmailAddress(userDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailService.send(userDto, "Test"));
        assertEquals("Not found email for user with id=1", exception.getMessage());
    }

    @Test
    void testSend_emailIsBlank_Negative() {
        userDto.setEmail("   ");
        doThrow(new IllegalArgumentException("Not found email for user with id=1"))
                .when(emailValidator)
                .checkUserEmailAddress(userDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailService.send(userDto, "Test"));
        assertEquals("Not found email for user with id=1", exception.getMessage());
    }

    @Test
    void testSend_emailNotValid_Negative() {
        userDto.setEmail("user@gmail.c5om");
        doThrow(new IllegalArgumentException("Not valid email " + userDto.getEmail() + " for user with id=1"))
                .when(emailValidator)
                .checkUserEmailAddress(userDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailService.send(userDto, "Test"));
        assertEquals("Not valid email " + userDto.getEmail() + " for user with id=1", exception.getMessage());
    }

    @Test
    void testGetPreferredContact_returnEmail() {
        assertEquals(UserDto.PreferredContact.EMAIL, emailService.getPreferredContact());
    }
}