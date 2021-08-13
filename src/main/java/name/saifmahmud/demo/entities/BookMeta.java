package name.saifmahmud.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BookMeta {
    @Id
    private Long id;

    @Column
    private Integer count;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Book book;
}
