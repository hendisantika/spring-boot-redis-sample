package id.my.hendisantika.springbootredissample.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 05/04/25
 * Time: 07.38
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@RedisHash
public class Category {
    @Id
    private String id;
    private String name;
}
