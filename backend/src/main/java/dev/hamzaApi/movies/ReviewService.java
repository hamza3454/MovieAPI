package dev.hamzaApi.movies;

// 2 ways to talk to a database, repository / template
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Used with classes that provide some business functionalities
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId, String email, String userName) { // look for the movie with the given id, then create new review
        Review review = reviewRepository.insert(new Review(reviewBody, email, userName));

        // using the template to perform an update call on the movie class
        // each movie has an empty array of reviewIds, these need to be updated
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review; // returning the review we just created
    }

    public boolean deleteReview(String reviewId, String userEmail) {
        try {
            ObjectId objectId = new ObjectId(reviewId); // ✅ Convert String to ObjectId
            System.out.println(reviewId);
            System.out.println(userEmail);
            // ✅ Find the review by ID and ensure ownership
            Optional<Review> review = reviewRepository.findById(objectId);

            if (review.isPresent()) {
                System.out.println(review);
                // ✅ Check if the logged-in user is the owner
                if (!review.get().getEmail().equals(userEmail)) {
                    System.out.println("I not found the email");
                    return false; // ❌ Not authorized
                }

                // ✅ Remove review from the Movie's reviewIds array
                mongoTemplate.update(Movie.class)
                        .matching(Criteria.where("reviewIds").in(objectId))
                        .apply(new Update().pull("reviewIds", objectId))
                        .first();

                // ✅ Delete the review itself
                reviewRepository.delete(review.get());
                return true; // ✅ Successfully deleted
            } else {
                return false; // ❌ Review does not exist
            }
        } catch (IllegalArgumentException e) {
            return false; // ❌ Invalid ObjectId format
        }
    }
}
