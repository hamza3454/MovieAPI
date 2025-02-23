package dev.hamzaApi.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal Jwt jwt // ✅ Extracts JWT automatically
    ) {
        // ✅ Extract user info from JWT
        String userId = jwt.getClaimAsString("sub"); // Unique user ID
        String userEmail = jwt.getClaimAsString("email"); // Email (if available)
        String userName = jwt.getClaimAsString("name");

        System.out.println("User ID: " + userId);
        System.out.println("User Email: " + userEmail);

        // ✅ Pass userId or userEmail with review
        Review review = reviewService.createReview(
                payload.get("reviewBody"),
                payload.get("imdbId"),
                userId, // Send user identity
                userName
        );

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

}
