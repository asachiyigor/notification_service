package faang.school.notificationservice.service.mail;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;

public class EmailService implements NotificationService {
    @Override
    public void send(UserDto user, String message) {

    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return null;
    }
}
