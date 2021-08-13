package name.saifmahmud.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import name.saifmahmud.demo.exceptions.BookAlreadyIssuedToUserException;
import name.saifmahmud.demo.exceptions.MaximumBooksIssuedException;
import name.saifmahmud.demo.exceptions.ZeroBooksLeftForIssueException;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Indexed
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @FullTextField
    private String title;

    @Column
    @FullTextField
    private String author;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date publishedAt;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private BookMeta meta;

    @ManyToMany
    @JsonIgnore
    private Set<User> users;

    public void addUser(User user) {
        if (getUsers().contains(user)) {
            throw new BookAlreadyIssuedToUserException();
        }

        if (user.getBooks().size() == 5) {
            throw new MaximumBooksIssuedException();
        }

        if (getUsers().size() == getMeta().getCount()) {
            throw new ZeroBooksLeftForIssueException();
        }

        getUsers().add(user);
    }
}
