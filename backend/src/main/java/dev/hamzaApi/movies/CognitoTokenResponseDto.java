package dev.hamzaApi.movies;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CognitoTokenResponseDto(String id_token,
                                      String access_token,
                                      String refresh_token,
                                      int expires_in,
                                      String token_type) {
}
