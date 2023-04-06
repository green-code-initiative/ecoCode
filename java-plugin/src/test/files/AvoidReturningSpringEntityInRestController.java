package fr.greencodeinitiative.java.checks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@RestController
public class AvoidReturningSpringEntityInRestController {
    private final BookService bookService;

    public AvoidReturningSpringEntityInRestController(BookService bookstoreService) {
        this.bookService = bookstoreService;
    }

    @RequestMapping("books")
    public List<Book> findBooks() { // Noncompliant {{Avoid returning a persistence Entity in a Spring RestController}}
        return bookService.findAll();
    }

    @RequestMapping("books/{id}")
    public Book findBookById(@PathVariable Long id) { // Noncompliant {{Avoid returning a persistence Entity in a Spring RestController}}
        return bookService.findById(id);
    }

    @Service
    public class BookService {
        private BookRepository bookRepository;

        public BookService(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        public List<Book> findAll() {
            return bookRepository.findAll();
        }
        public Book findById(Long id) {
            return bookRepository.findById(id).orElse(null);
        }
    }

    @Repository
    public interface BookRepository extends JpaRepository<Book, Long> {
    }

    @Entity
    public class Book implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}