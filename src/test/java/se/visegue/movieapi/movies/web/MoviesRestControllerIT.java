package se.visegue.movieapi.movies.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.visegue.movieapi.errors.InvalidResourceException;
import se.visegue.movieapi.errors.ResourceNotFoundException;
import se.visegue.movieapi.movies.dao.MovieEntity;
import se.visegue.movieapi.movies.domain.MovieService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static se.visegue.movieapi.movies.MovieFixture.createMovie;

@WebMvcTest
class MoviesRestControllerIT {

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void givenMovie_whenGettingMovie_shouldGetStatusOkWithMovie() throws Exception {
        // Given
        given(createMovie(3L, "TestMovie", "Test genre1|Test genre2"));

        // When
        // Then
        mockMvc.perform(get("/movies/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("TestMovie"))
                .andExpect(jsonPath("$.genres[0]").value("Test genre1"))
                .andExpect(jsonPath("$.genres[1]").value("Test genre2"))
                .andExpect(jsonPath("$.version").exists());
    }

    @Test
    public void givenNoMovie_whenGettingMovie_shouldGetStatusNotFound() throws Exception {
        // Given
        givenNoMovie(3L);

        // When
        // Then
        mockMvc.perform(get("/movies/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenMovies_whenFindingMovies_shouldGetAllMovies() throws Exception {
        // Given
        given(Arrays.asList(
                createMovie(3L, "Movie3", ""),
                createMovie(5L, "Movie5", "")));

        // When
        // Then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void givenNoMovies_whenFindingMovies_shouldGetEmptyList() throws Exception {
        // Given
        given(new ArrayList<>());

        // When
        // Then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenNewMovie_whenCreatingMovie_shouldReturnCreated() throws Exception {
        // Given
        when(movieService.createMovie(any(MovieEntity.class))).thenReturn(new MovieEntity());

        // When
        // Then
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie("Title", "Genre"))))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void givenInvalidResource_whenCreatingMovie_shouldReturn422() throws Exception {
        // Given
        when(movieService.createMovie(any(MovieEntity.class))).thenThrow(new InvalidResourceException("Invalid resource"));

        // When
        // Then
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie("Title", ""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNoTitle_whenCreatingMovie_shouldReturn400() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie(null, ""))))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenEmptyTitle_whenCreatingMovie_shouldReturn400() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie("", ""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidResource_whenUpdatingMovie_shouldReturn400() throws Exception {
        // Given
        when(movieService.updateMovie(eq(3L), any(MovieEntity.class))).thenThrow(new InvalidResourceException("Invalid resource"));

        // When
        // Then
        mockMvc.perform(put("/movies/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie(3L, "Title", "genre"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNoTitle_whenUpdatingMovie_shouldReturn400() throws Exception {
        // Given
        when(movieService.updateMovie(eq(3L), any(MovieEntity.class))).thenThrow(new InvalidResourceException("Invalid resource"));

        // When
        // Then
        mockMvc.perform(put("/movies/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie(3L, "Title", "genre"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenEmptyTitle_whenUpdatingMovie_shouldReturn400() throws Exception {
        // Given
        when(movieService.updateMovie(eq(3L), any(MovieEntity.class))).thenThrow(new InvalidResourceException("Invalid resource"));

        // When
        // Then
        mockMvc.perform(put("/movies/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie(3L, "Title", "genre"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenMovie_whenUpdating_shouldReturn204() throws Exception {
        // Given
        when(movieService.updateMovie(eq(3L), any(MovieEntity.class))).thenReturn(new MovieEntity());

        // When
        // Then
        mockMvc.perform(put("/movies/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createMovie(3L, "Title", "genre"))))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenMovie_whenDeleting_shouldReturn204() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform(delete("/movies/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenNoMovie_whenDeleting_shouldReturn404() throws Exception {
        // Given
        doThrow(new ResourceNotFoundException("Movie", 3L)).when(movieService).deleteById(3L);
        // When
        // Then
        mockMvc.perform(delete("/movies/3"))
                .andExpect(status().isNotFound());
    }


    /**
     * Helper method for setting up test properties given that a certain movie exists.
     *
     * @param movie The movie that exists
     */
    private void given(MovieEntity movie) {
        given(List.of(movie));
    }

    /**
     * Helper method for setting up test properties given that certain movies exists.
     *
     * @param movies The movies that exists.
     */
    private void given(List<MovieEntity> movies) {
        movies.forEach(movie -> when(movieService.findById(movie.getId())).thenReturn(movie));
        when(movieService.findAll()).thenReturn(movies);
    }

    /**
     * Helper method for setting up test properties given that no movie with specified id exist.
     *
     * @param movieId Movie id that should not exist.
     */
    private void givenNoMovie(Long movieId) {
        when(movieService.findById(movieId)).thenThrow(new ResourceNotFoundException("Movie", movieId));
    }


}
