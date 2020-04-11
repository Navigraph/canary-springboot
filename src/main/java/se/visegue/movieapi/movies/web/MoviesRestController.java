package se.visegue.movieapi.movies.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.visegue.movieapi.movies.dao.Movie;
import se.visegue.movieapi.movies.domain.MovieService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
    public List<Movie> findMovies() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public ResponseEntity createMovie(@RequestBody @Validated Movie movie) throws URISyntaxException {
        Movie createdMoved = movieService.createMovie(movie);
        return ResponseEntity.created(new URI("/movies/" + createdMoved.getId())).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMovie(@PathVariable Long id, @RequestBody @Validated Movie movie) {
        movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }
}
