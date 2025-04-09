package dev.starzynski.trendify_backend.Service;

import dev.starzynski.trendify_backend.DTO.UserDTO;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.ReplyRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;


    public ResponseEntity<?> createUser(String username, String email, String password, String profilePicture) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        if (profilePicture != null) {
            user.setProfilePicture(profilePicture);
        }

        try {
            Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
            Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

            if (existingUsername.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "This username is already in use."));
            }
            if (existingEmail.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "This e-mail is already in use."));
            }

            userRepository.insert(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("jwt", jwtService.generateToken(user.getUsername())));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> loginUser(String username, String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(Collections.singletonMap("jwt", jwtService.generateToken(user.getUsername())));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Failed to log in. Please try again."));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Server error. Please try again."));
    }

    public ResponseEntity<?> followUser(String jwt, String targetUsername) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);
            Optional<User> optionalTarget = userRepository.findByUsername(targetUsername);

            if (optionalUser.isEmpty() || optionalTarget.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed to follow user. Please try again."));
            }

            User user = optionalUser.get();
            User target = optionalTarget.get();

            boolean alreadyFollowing = user.getFollowing().stream()
                    .anyMatch(followingUser -> followingUser.equals(target.getId()));

            if (alreadyFollowing) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You are already following this user."));
            }

            user.getFollowing().add(target.getId());
            userRepository.save(user);

            target.getFollowers().add(user.getId());
            userRepository.save(target);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Followed!"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> unfollowUser(String jwt, String targetUsername) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);
            Optional<User> optionalTarget = userRepository.findByUsername(targetUsername);

            if (optionalUser.isEmpty() || optionalTarget.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed to follow user. Please try again."));
            }

            User user = optionalUser.get();
            User target = optionalTarget.get();

            boolean alreadyFollowing = user.getFollowing().stream()
                    .anyMatch(followingUser -> followingUser.equals(target.getId()));

            if (!alreadyFollowing) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You haven't followed this user yet."));
            }

            user.getFollowing().removeIf(followedUser -> followedUser.equals(target.getId()));
            userRepository.save(user);

            target.getFollowers().removeIf(followingUser -> followingUser.equals(user.getId()));
            userRepository.save(target);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Unfollowed!"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> getUserData(String jwt) {
        try {
            String username = jwtService.extractUsername(jwt);
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed to get user data. Please try again."));
            }

            UserDTO user = new UserDTO(optionalUser.get(), postRepository, replyRepository, userRepository);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("data", user));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    /*
    public ResponseEntity<?> getUserByUsername(String username) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "This user doesn't exist."));
            }

            User user = optionalUser.get();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("data", user));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    } */
}
