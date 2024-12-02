package faang.school.notificationservice.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisSmsProperties(Integer port, String host, String userSmsTopic) {
}
