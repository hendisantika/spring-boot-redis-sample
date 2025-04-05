package id.my.hendisantika.springbootredissample.controller;

import id.my.hendisantika.springbootredissample.model.Book;
import id.my.hendisantika.springbootredissample.model.Category;
import id.my.hendisantika.springbootredissample.repository.BookRepository;
import id.my.hendisantika.springbootredissample.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-redis-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 05/04/25
 * Time: 07.48
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    /**
     * Retrieves a paginated list of books.
     * Caches the result to avoid hitting the database on repeated requests with the same parameters.
     *
     * @param page the page number to retrieve (default is 0).
     * @param size the number of items per page (default is 10).
     * @return a ResponseEntity containing the paginated books, page number, total pages, and total elements.
     */
    @GetMapping("/books")
    @Cacheable(value = "booksCache", key = "#page + '-' + #size")
    public ResponseEntity<Map<String, Object>> getBooks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Book> pagedResult = bookRepository.findAll(paging);
        List<Book> books = pagedResult.hasContent() ? pagedResult.getContent() : Collections.emptyList();

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        response.put("page", pagedResult.getNumber());
        response.put("pages", pagedResult.getTotalPages());
        response.put("total", pagedResult.getTotalElements());

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieves all categories.
     * Caches the result to avoid hitting the database on repeated requests.
     *
     * @return an Iterable of categories.
     */
    @GetMapping("/categories")
    @Cacheable(value = "categoriesCache")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
