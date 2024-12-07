package faang.school.notificationservice.validator.mail;

import faang.school.notificationservice.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    private final UserDto userDto = getUserDto();

    @Test
    void testCheckUserEmailAddress_Positive() {
        assertDoesNotThrow(() -> emailValidator.checkUserEmailAddress(userDto));
    }

    @Test
    void testCheckUserEmailAddress_emailIsNull_Negative() {
        userDto.setEmail(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailValidator.checkUserEmailAddress(userDto));
        assertEquals("Not found email for user with id=1", exception.getMessage());
    }

    @Test
    void testCheckUserEmailAddress_emailIsBlank_Negative() {
        userDto.setEmail("   ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailValidator.checkUserEmailAddress(userDto));
        assertEquals("Not found email for user with id=1", exception.getMessage());
    }

    @Test
    void testCheckUserEmailAddress_emailNotValid_Negative() {
        userDto.setEmail("user@gmail.c5om");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailValidator.checkUserEmailAddress(userDto));
        assertEquals("Not valid email " + userDto.getEmail() + " for user with id=1", exception.getMessage());
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .email("username@gmail.com")
                .build();
    }
}