package dev.hamzaApi.movies;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateController {

    @GetMapping("/name")
    public ResponseEntity<MessageDto> privateMessages(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(new MessageDto(jwt.getClaim("name")));
    }
}
