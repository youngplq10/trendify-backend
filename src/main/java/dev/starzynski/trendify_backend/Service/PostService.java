package dev.starzynski.trendify_backend.Service;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public ResponseEntity<?> createPost(String content, String image, String jwt) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed creating post. Please try again."));
            }

            Post post = new Post();
            post.setUser(optionalUser.get());
            post.setContent(content);

            if (image != null) {
                post.setImageLink(image);
            }

            postRepository.insert(post);

            optionalUser.get().getPosts().add(post);
            userRepository.save(optionalUser.get());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("unique", post.getUnique()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }
}
