package dev.hamzaApi.movies;

// uses repo class to talks to database and gets list of movies

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired // lets the framework know that we want to initialize moveRepo
    private MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId) { // we say optional in case movie is NULL
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
