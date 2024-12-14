package faang.school.notificationservice.config.vonage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vonage.api")
public record VonageSmsProperties(String apiKey, String apiSecret) {
}
