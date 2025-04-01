package dev.starzynski.trendify_backend.Service;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

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
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> likePost(String jwt, String postUnique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            Optional<Post> optionalPost = postRepository.findByUnique(postUnique);

            if (optionalUser.isEmpty() || optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed in liking post. Please try again."));
            }

            User user = optionalUser.get();
            Post post = optionalPost.get();

            boolean alreadyLiked = post.getLikes().stream()
                    .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

            if (alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You already liked this post."));
            }

            post.getLikes().add(user);
            postRepository.save(post);

            user.getLikedPosts().add(post);
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Liked."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> unlikePost(String jwt, String postUnique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            Optional<Post> optionalPost = postRepository.findByUnique(postUnique);

            if (optionalUser.isEmpty() || optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed in unliking post. Please try again."));
            }

            User user = optionalUser.get();
            Post post = optionalPost.get();

            boolean alreadyLiked = post.getLikes().stream()
                    .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

            if (!alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You haven't liked this post yet."));
            }

            post.getLikes().removeIf(likedUser -> likedUser.getId().equals(user.getId()));
            postRepository.save(post);

            user.getLikedPosts().removeIf(likedPost -> likedPost.getId().equals(post.getId()));
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Unliked"));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> getAllPosts() {
        try {
            return ResponseEntity
                    .status(200)
                    .body(Collections.singletonMap("posts", postRepository.findAllByOrderByCreatedAtDateDesc()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> getPostByUnique(String unique) {
        try {
            Optional<Post> optionalPost = postRepository.findByUnique(unique);

            if (optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "This post doesn't exist."));
            }

            Post post = optionalPost.get();

            post.getReplies().sort(Comparator.comparing(Reply::getCreatedAtDate).reversed());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("data", post));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> deletePost(String jwt, String unique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);
            Optional<Post> optionalPost = postRepository.findByUnique(unique);

            if (optionalUser.isEmpty() || optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Error in deleting post. Please try again."));
            }

            User user = optionalUser.get();
            Post post = optionalPost.get();

            Boolean isTheUserOwner = post.getUser().getId().equals(user.getId());

            if (!isTheUserOwner) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Error in deleting post. Please try again."));
            } else {
                postRepository.delete(post);
                user.getPosts().removeIf(usersPosts -> Objects.equals(usersPosts.getUnique(), unique));

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(Collections.singletonMap("message", "Post deleted."));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }
}
