package faang.school.notificationservice.config.email;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public record MailProperties(
        @Value("${spring.mail.host}")
        String host,

        @Value("${spring.mail.port}")
        int port,

        @Value("${spring.mail.username}")
        String username,

        @Value("${spring.mail.password}")
        String password,

        @Value("${spring.mail.protocol}")
        String protocol,

        @Value("${spring.mail.smtp.starttls.enable}")
        boolean starttls,

        @Value("${spring.mail.smtp.auth}")
        boolean auth,

        @Value("${spring.mail.debug}")
        boolean debug) {

}
