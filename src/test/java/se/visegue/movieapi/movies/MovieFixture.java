package se.visegue.movieapi.movies;

import se.visegue.movieapi.movies.dao.Movie;
import se.visegue.movieapi.utils.StringListConverter;

public class MovieFixture {

    /**
     * Factory method for creating a new {@link Movie}.
     * @param id        The id of the movie.
     * @param title     The title of the movie.
     * @param genres    Genres of the movie, separated by |.
     * @return A new {@link Movie}.
     */
    public static Movie createMovie(Long id, String title, String genres) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenres(new StringListConverter().convertToEntityAttribute(genres));
        return movie;
    }

    /**
     * Factory method for creating new movies that is persistable (without id).
     * @param title     The title of the movie
     * @param genres    Genres of the movie, separated by |.
     * @return A {@link Movie} that is persistable
     */
    public static Movie createMovie(String title, String genres) {
        return createMovie(null, title, genres);
    }

    /**
     * Factory method for creating a {@link Movie} with only id set.
     * @param id    id for the new movie.
     * @return a {@link Movie}
     */
    public static Movie createMovie(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }
}
