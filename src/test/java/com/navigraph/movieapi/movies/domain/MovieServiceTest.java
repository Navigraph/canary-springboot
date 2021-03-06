package com.navigraph.movieapi.movies.domain;

import com.navigraph.movieapi.movies.MovieFixture;
import com.navigraph.movieapi.movies.dao.MovieEntity;
import com.navigraph.movieapi.movies.dao.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.navigraph.movieapi.errors.InvalidResourceException;
import com.navigraph.movieapi.errors.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static com.navigraph.movieapi.movies.MovieFixture.createMovie;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    public MovieService movieService;


    @Test
    public void givenMovie_whenFindingMovieById_shouldGetMovie() {
        // Given
        when(movieRepository.findById(3L)).thenReturn(Optional.of(MovieFixture.createMovie(3L)));
        // When
        MovieEntity movie = movieService.findById(3L);
        // Then
        List<MovieEntity> all = movieRepository.findAll();
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(3L);
        Mockito.verify(movieRepository, times(1)).findById(3L);
    }

    @Test
    public void givenNoMovies_whenFindingMovieById_shouldThrowResourceNotFoundException() {
        // Gven
        when(movieRepository.findById(5L)).thenReturn(Optional.empty());
        // When
        // Then
        assertThatThrownBy(() -> movieService.findById(5L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("5");
    }

    @Test
    public void givenNoMovies_whenFindingAllMovies_shouldGetEmptyList() {
        // Given
        when(movieRepository.findAll()).thenReturn(new ArrayList<>());

        // Then
        List<MovieEntity> movies = movieService.findAll();

        // Then
        assertThat(movies).isEmpty();
    }

    @Test
    public void givenMovies_whenFindingAllMovies_shouldGetMoview() {
        // Given
        when(movieRepository.findAll()).thenReturn(Arrays.asList(MovieFixture.createMovie(1L), MovieFixture.createMovie(3L)));

        // When
        List<MovieEntity> movies = movieService.findAll();

        // Then
        assertThat(movies).extracting(MovieEntity::getId).containsExactlyInAnyOrder(1L, 3L);
    }

    @Test
    public void givenMovieWithId_whenCreatingMovie_shouldThrowInvalidResourceException() {
        // Given
        MovieEntity movie = MovieFixture.createMovie(1L);

        // When
        // Then
        assertThatThrownBy(() -> movieService.createMovie(movie))
                .isInstanceOf(InvalidResourceException.class)
                .hasMessageContaining("id");
    }

    @Test
    public void givenNewMovie_whenCreatingMovie_shouldReturnMovie() {
        // Given
        MovieEntity newMovie = new MovieEntity();
        MovieEntity persistedMovie = new MovieEntity();
        when(movieRepository.save(newMovie)).thenReturn(persistedMovie);

        // When
        MovieEntity movie = movieService.createMovie(newMovie);

        // Then
        assertThat(movie).isSameAs(persistedMovie);
    }

    @Test
    public void givenNoExistingMovie_whenUpdating_shouldThrowResourceNotFoundException() {
        // Given
        when(movieRepository.findById(3L)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> movieService.updateMovie(3L, new MovieEntity()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("3");
    }

    @Test
    public void givenExistingMovie_whenUpdatingMovieId_shouldThrowInvalidResourceException() {
        // Given
        when(movieRepository.findById(3L)).thenReturn(Optional.of(MovieFixture.createMovie(3L)));

        // When
        // Then
        assertThatThrownBy(() -> movieService.updateMovie(3L, MovieFixture.createMovie(5L)))
                .isInstanceOf(InvalidResourceException.class)
                .hasMessageContaining("id");
    }

    @Test
    public void givenExistingMovie_whenUpdatingMovie_shouldReturnMovie() {
        // Given
        MovieEntity existingMovie = MovieFixture.createMovie(3L);
        MovieEntity expected = new MovieEntity();
        when(movieRepository.findById(3L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(expected);

        // When
        MovieEntity actual = movieService.updateMovie(3L, MovieFixture.createMovie(3L));

        // Then
        assertThat(actual).isSameAs(expected);
    }

    @Test
    public void givenMovie_whenDeletingMovie_shouldNotThrow() {
        // Given
        when(movieRepository.existsById(3L)).thenReturn(true);

        // When
        movieService.deleteById(3L);

        // Then
        verify(movieRepository).deleteById(3L);
    }

    @Test
    public void givenNoExistingMovie_whenDeletingMovie_shouldThrowResourceNotFoundException() {
        // Given
        when(movieRepository.existsById(3L)).thenReturn(false);

        // When
        // Then
        assertThatThrownBy(() -> movieService.deleteById(3L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("id");
    }
}
