package faang.school.notificationservice.config;

import faang.school.notificationservice.listener.FollowerEventListener;
import faang.school.notificationservice.listener.RecommendationReceivedListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.channel.follower}")
    private String followerChannel;

    @Value("${spring.data.redis.channel.recommendation}")
    private String recommendationChannel;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    MessageListenerAdapter followerListener(FollowerEventListener followerEventListener){
        return new MessageListenerAdapter(followerEventListener);
    }

    @Bean
    MessageListenerAdapter recommendationListener(RecommendationReceivedListener recommendationReceivedListener){
        return new MessageListenerAdapter(recommendationReceivedListener);
    }

    @Bean
    ChannelTopic followerTopic() {
        return new ChannelTopic(followerChannel);
    }

    @Bean
    ChannelTopic recommendationTopic(){
        return new ChannelTopic(recommendationChannel);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter followerListener,
                                                 MessageListenerAdapter recommendationListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(followerListener, followerTopic());
        container.addMessageListener(recommendationListener, recommendationTopic());
//        add other topics
//        container.addMessageListener(...);
        return container;
    }
}
