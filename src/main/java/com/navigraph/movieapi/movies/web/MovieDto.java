package com.navigraph.movieapi.movies.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class MovieDto implements Serializable {
    private static final long serialVersionUID = 867612787984411947L;

    private Long id;
    @NotNull
    @NotEmpty
    private String title;
    private List<String> genres = new ArrayList<>();
    private int version = 0;
}
