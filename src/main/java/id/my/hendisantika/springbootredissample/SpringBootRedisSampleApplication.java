package id.my.hendisantika.springbootredissample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching // Enables Spring’s annotation-driven cache management capability
@SpringBootApplication
public class SpringBootRedisSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisSampleApplication.class, args);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
