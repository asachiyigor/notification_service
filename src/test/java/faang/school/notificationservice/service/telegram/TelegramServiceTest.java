package faang.school.notificationservice.service.telegram;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.config.telegram.bot.TelegramBot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramServiceTest {
    @Mock
    TelegramBot telegramBot;

    @InjectMocks
    TelegramService telegramService;

    @Test
    @DisplayName("Test method send in TelegramService")
    void testSend() {
        UserDto userDto = UserDto.builder().id(1L).build();
        String message = "Hello World";
        telegramService.send(userDto, message);
        verify(telegramBot, times(1)).sendText(userDto.getId(), message);
    }

    @Test
    @DisplayName("Test method send in TelegramService")
    void testNegativeSend() {
        UserDto userDto = UserDto.builder().id(-1L).build();
        UserDto userDtoNull = null;
        String message = "Hello World";
        assertThrows(IllegalArgumentException.class, () -> telegramService.send(userDto, message));
        assertThrows(IllegalArgumentException.class, () -> telegramService.send(userDtoNull, message));
    }
}