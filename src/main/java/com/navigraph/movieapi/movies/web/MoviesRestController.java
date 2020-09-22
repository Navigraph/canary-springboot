package com.navigraph.movieapi.movies.web;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.navigraph.movieapi.movies.dao.MovieEntity;
import com.navigraph.movieapi.movies.domain.MovieService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The RestController is using the Movie entity directly.
 * In a real scenario this would probably be mapped to a DTO.
 */
@RestController
@RequestMapping("/movies")
public class MoviesRestController {

    private MovieService movieService;

    public MoviesRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDto> findMovies() {
        return movieService.findAll().stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return dtoFromEntity(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity createMovie(@RequestBody @Validated MovieDto movie) throws URISyntaxException {
        MovieEntity createdMoved = movieService.createMovie(entityFromDto(movie));
        return ResponseEntity.created(new URI("/movies/" + createdMoved.getId())).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMovie(@PathVariable Long id, @RequestBody @Validated MovieDto movie) {
        movieService.updateMovie(id, entityFromDto(movie));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    //
    // In a real case the serialization/deserialization would be done in a separate object for testability.
    //

    /**
     * Creates a {@link MovieDto} from a {@link MovieEntity}
     * @param entity The entity to map.
     * @return A {@link MovieDto}
     */
    private MovieDto dtoFromEntity(MovieEntity entity) {
        var dto = new MovieDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * Creates a {@link MovieEntity} from a  {@link MovieDto}
     * @param dto
     * @return a {@link MovieEntity}
     */
    private MovieEntity entityFromDto(MovieDto dto) {
        var entity = new MovieEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
