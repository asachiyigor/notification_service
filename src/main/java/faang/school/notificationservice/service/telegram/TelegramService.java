package faang.school.notificationservice.service.telegram;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;
import faang.school.notificationservice.config.telegram.bot.TelegramBot;

public class TelegramService implements NotificationService {
    TelegramBot telegramBot;

    @Override
    public void send(UserDto user, String message) {
        validationUserDto(user);
        Long chatId = user.getId(); //здесь должен быть id чата с пользователем, который пользователь сам инициировал
        telegramBot.sendText(chatId, message);
    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.TELEGRAM;
    }

    private void validationUserDto(UserDto userDto) {
        if (userDto == null || userDto.getId() <= 0) {
            throw new IllegalArgumentException("userDto not valid");
        }
    }
}
