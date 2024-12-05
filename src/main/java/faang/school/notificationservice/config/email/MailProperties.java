package faang.school.notificationservice.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
public record MailProperties(
        String host,
        int port,
        String username,
        String password,
        String protocol,
        @Value("${spring.mail.smtp.auth}")
        boolean auth,
        @Value("${spring.mail.smtp.starttls.enable}")
        boolean starttls,
        boolean debug) {
}
