package se.visegue.movieapi.movies.dao;

import lombok.*;
import se.visegue.movieapi.utils.StringListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @NotEmpty
    private String title;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> genres = new ArrayList<>();

    @Version
    @Column
    private int version = 0;
}
