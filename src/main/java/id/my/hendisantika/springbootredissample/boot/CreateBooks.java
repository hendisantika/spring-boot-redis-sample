package id.my.hendisantika.springbootredissample.boot;

import id.my.hendisantika.springbootredissample.repository.BookRepository;
import id.my.hendisantika.springbootredissample.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 05/04/25
 * Time: 07.44
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Order(3)
@Component
@RequiredArgsConstructor
public class CreateBooks implements CommandLineRunner {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;
}
