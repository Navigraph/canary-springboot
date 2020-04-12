package se.visegue.movieapi.movies.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static se.visegue.movieapi.movies.MovieFixture.createMovie;

/**
 * Test for trying out the hibernate entity mapping.
 * We don't need to test every function of the repository, in that sense we trust spring-data-jpa.
 * What we want to test here is the mapping of our entity towards the database.
 * The test are relying on @DataJpaTest to roll back the trancations (as the tests are transactional).
 * In a real scenario is is not ideal, but for this use case it is fine.
 */
@DataJpaTest
@TestPropertySource(properties = {"spring.datasource.data=data-test.sql"})
class MovieRepositoryIT {

    private static final List<String> MOVIE_TITLES = Arrays.asList("Toy Story (1995)", "Jumanji (1995)");
    private static final List<String> TOY_STORY_GENRES = Arrays.asList("Adventure", "Animation", "Children", "Comedy", "Fantasy");

    @Autowired
    private MovieRepository repository;

    @Test
    public void givenMovies_whenFetchingAll_shouldGetMovies() {
        // Given
        // When
        List<MovieEntity> movies = repository.findAll();

        // Then
        assertThat(movies).extracting(MovieEntity::getTitle).containsExactlyInAnyOrderElementsOf(MOVIE_TITLES);
    }

    @Test
    public void givenMovies_whenFetchingToyStory_shouldGetToyStory() {
        // Given
        // When
        Optional<MovieEntity> actual = repository.findById(1L);

        //Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(actual).isPresent();
        assertions.assertThat(actual.get().getTitle()).isEqualTo(MOVIE_TITLES.get(0));
        assertions.assertThat(actual.get().getGenres()).containsExactlyInAnyOrderElementsOf(TOY_STORY_GENRES);
        assertions.assertAll();
    }

    @Test
    public void givenMovies_whenSavingPersistedMovie_shouldUpdateVersion() {
        // Given
        Optional<MovieEntity> actual = repository.findById(1L);
        int originalVersion = actual.get().getVersion();

        // When
        actual.get().setTitle("New title");
        int savedVersion = repository.saveAndFlush(actual.get()).getVersion();

        //Then
        assertThat(originalVersion).isLessThan(savedVersion);
    }

    @Test
    public void whenFetchingUnknownMovie_shouldGetEmptyOptional() {
        // Given
        // When
        // Then
        assertThat(repository.findById(Long.MAX_VALUE)).isNotPresent();
    }

    @Test
    public void givenUnpersistedMovie_whenSavingMovie_shouldReturnWithGeneratedId() {
        // Given
        MovieEntity movie = createMovie("New movie", "Genre1|Genre2");

        // When
        MovieEntity savedMovie = repository.save(movie);

        // Then
        assertThat(savedMovie.getId()).isNotNull();
    }

    @Test
    public void givenUnpersistedMovie_whenSavingMovie_shouldBeSearchable() {
        // Given
        MovieEntity movie = createMovie("New movie", "Genre3|Genre4");

        // When
        MovieEntity savedMovie = repository.save(movie);

        // Then
        Optional<MovieEntity> searchedMovie = repository.findById(savedMovie.getId());

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(searchedMovie).isPresent();
        assertions.assertThat(searchedMovie.get().getId()).isNotNull();
        assertions.assertThat(searchedMovie.get().getTitle()).isEqualTo("New movie");
        assertions.assertThat(searchedMovie.get().getGenres()).containsExactlyInAnyOrder("Genre3", "Genre4");
        assertions.assertAll();
    }

    @Test
    public void givenUnpersistedMovie_whenSavingMovie_shouldHave0Version() {
        // Given
        MovieEntity movie = createMovie("New Movie", "");

        // When
        MovieEntity saved = repository.save(movie);

        // Then
        assertThat(saved.getVersion()).isEqualTo(0);
    }


}
