package dev.starzynski.trendify_backend.Service;

import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public ResponseEntity<?> createUser(User user) {
        try {
            Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
            Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

            if (existingUsername.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("message", "This username is already in use."));
            }
            if (existingEmail.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("message", "This e-mail is already in use."));
            }

            userRepository.insert(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", jwtService.generateToken(user.getUsername())));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Server error. Please try again."));
        }
    }
}
