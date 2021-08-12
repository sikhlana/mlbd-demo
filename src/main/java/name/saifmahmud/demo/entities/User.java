package name.saifmahmud.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import name.saifmahmud.demo.exceptions.BookNotIssuedToUserException;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book> books;

    public void removeBook(Book book) {
        if (!getBooks().remove(book)) {
            throw new BookNotIssuedToUserException();
        }

        book.getUsers().remove(this);
    }
}
