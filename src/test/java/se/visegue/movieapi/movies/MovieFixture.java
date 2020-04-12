package se.visegue.movieapi.movies;

import se.visegue.movieapi.movies.dao.MovieEntity;
import se.visegue.movieapi.utils.StringListConverter;

public class MovieFixture {

    /**
     * Factory method for creating a new {@link MovieEntity}.
     * @param id        The id of the movie.
     * @param title     The title of the movie.
     * @param genres    Genres of the movie, separated by |.
     * @return A new {@link MovieEntity}.
     */
    public static MovieEntity createMovie(Long id, String title, String genres) {
        MovieEntity movie = new MovieEntity();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenres(new StringListConverter().convertToEntityAttribute(genres));
        return movie;
    }

    /**
     * Factory method for creating new movies that is persistable (without id).
     * @param title     The title of the movie
     * @param genres    Genres of the movie, separated by |.
     * @return A {@link MovieEntity} that is persistable
     */
    public static MovieEntity createMovie(String title, String genres) {
        return createMovie(null, title, genres);
    }

    /**
     * Factory method for creating a {@link MovieEntity} with only id set.
     * @param id    id for the new movie.
     * @return a {@link MovieEntity}
     */
    public static MovieEntity createMovie(Long id) {
        MovieEntity movie = new MovieEntity();
        movie.setId(id);
        return movie;
    }
}
