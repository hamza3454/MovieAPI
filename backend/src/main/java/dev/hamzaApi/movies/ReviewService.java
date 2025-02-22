package dev.hamzaApi.movies;

// 2 ways to talk to a database, repository / template

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service // Used with classes that provide some business functionalities
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) { // look for the movie with the given id, then create new review
        Review review = reviewRepository.insert(new Review(reviewBody));

        // using the template to perform an update call on the movie class
        // each movie has an empty array of reviewIds, these need to be updated
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review; // returning the review we just created
    }
}
