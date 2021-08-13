package name.saifmahmud.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import name.saifmahmud.demo.exceptions.BookAlreadyIssuedToUserException;
import name.saifmahmud.demo.exceptions.MaximumBooksIssuedException;
import name.saifmahmud.demo.exceptions.ZeroBooksLeftForIssueException;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Indexed
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @ToString.Include
    @FullTextField
    private String title;

    @Column
    @FullTextField
    private String author;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date publishedAt;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    private BookMeta meta = null;

    @JsonIgnore
    @ManyToMany
    private Set<User> users;

    public void setMeta(@Nullable BookMeta meta) {
        if (meta == null) {
            if (this.meta != null) {
                this.meta.setBook(null);
            }
        } else {
            meta.setBook(this);
        }

        this.meta = meta;
    }

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
