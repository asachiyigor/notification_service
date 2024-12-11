package faang.school.notificationservice.dto;

import com.vonage.client.account.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String aboutMe;
    private String city;
    private Country country;
    private String phone;
    private List<Long> participatedEventIds;
    private PreferredContact preference;
    private Locale locale;

    public enum PreferredContact {
        EMAIL, SMS, TELEGRAM, PHONE
    }
}
