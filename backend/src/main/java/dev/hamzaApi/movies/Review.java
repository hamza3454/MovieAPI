package dev.hamzaApi.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    private ObjectId id;
    private String body;
    private String email;
    private String name;

    public Review(String body, String email, String name) {
        this.body = body;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id.toHexString(); // Converts ObjectId to string
    }
}
