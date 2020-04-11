package se.visegue.movieapi.movies.domain;

import org.springframework.stereotype.Service;
import se.visegue.movieapi.errors.InvalidResourceException;
import se.visegue.movieapi.movies.dao.Movie;
import se.visegue.movieapi.movies.dao.MovieRepository;
import se.visegue.movieapi.errors.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {

    private MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public Movie createMovie(Movie movie) {
        if (movie.getId() != null) {
            throw new InvalidResourceException("Should not set id explicitly when creating movie, will be generated");
        }

        return repository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = findById(id);
        if (!existingMovie.getId().equals(movie.getId())) {
            throw new InvalidResourceException("Not allowed to change movie id");
        }

        return repository.save(movie);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", id);
        }
        repository.deleteById(id);
    }
}
