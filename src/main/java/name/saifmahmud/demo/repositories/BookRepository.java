package name.saifmahmud.demo.repositories;

import name.saifmahmud.demo.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findByTitleContainingIgnoreCase(String title);

    Iterable<Book> findByAuthorContainingIgnoreCase(String author);
}
