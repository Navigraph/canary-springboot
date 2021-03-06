package com.navigraph.movieapi.movies.domain;

import org.springframework.stereotype.Service;
import com.navigraph.movieapi.errors.InvalidResourceException;
import com.navigraph.movieapi.movies.dao.MovieEntity;
import com.navigraph.movieapi.movies.dao.MovieRepository;
import com.navigraph.movieapi.errors.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {

    private MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    public List<MovieEntity> findAll() {
        return repository.findAll();
    }

    public MovieEntity createMovie(MovieEntity movie) {
        if (movie.getId() != null) {
            throw new InvalidResourceException("Should not set id explicitly when creating movie, will be generated");
        }

        return repository.save(movie);
    }

    public MovieEntity updateMovie(Long id, MovieEntity movie) {
        MovieEntity existingMovie = findById(id);
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
