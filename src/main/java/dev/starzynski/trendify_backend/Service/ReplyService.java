package dev.starzynski.trendify_backend.Service;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.ReplyRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<?> createReply(String postUnique, String jwt, String imageLink, String content) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            Optional<Post> optionalPost = postRepository.findByUnique(postUnique);

            if (optionalUser.isEmpty() || optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed creating reply. Please try again."));
            }

            Reply reply = new Reply();

            reply.setContent(content);
            reply.setUserId(optionalUser.get().getId());
            reply.setPostId(optionalPost.get().getId());

            if (imageLink != null) {
                reply.setImageLink(imageLink);
            }

            replyRepository.insert(reply);

            optionalUser.get().getReplies().add(reply.getId());
            userRepository.save(optionalUser.get());

            optionalPost.get().getReplies().add(reply.getId());
            postRepository.save(optionalPost.get());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("unique", reply.getUnique()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> likeReply(String jwt, String replyUnique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            Optional<Reply> optionalReply = replyRepository.findByUnique(replyUnique);

            if (optionalUser.isEmpty() || optionalReply.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed in liking reply. Please try again."));
            }

            User user = optionalUser.get();
            Reply reply = optionalReply.get();

            boolean alreadyLiked = reply.getLikes().stream()
                    .anyMatch(like -> like.equals(user.getId()));

            if (alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You already liked this reply."));
            }

            reply.getLikes().add(user.getId());
            replyRepository.save(reply);

            user.getLikedReplies().add(reply.getId());
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

    public ResponseEntity<?> unlikeReply(String jwt, String replyUnique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            Optional<Reply> optionalReply = replyRepository.findByUnique(replyUnique);

            if (optionalUser.isEmpty() || optionalReply.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Failed in unliking reply. Please try again."));
            }

            User user = optionalUser.get();
            Reply reply = optionalReply.get();

            boolean alreadyLiked = reply.getLikes().stream()
                    .anyMatch(like -> like.equals(user.getId()));

            if (!alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "You haven't liked this reply yet."));
            }

            reply.getLikes().removeIf(like -> like.equals(user.getId()));
            replyRepository.save(reply);

            user.getLikedReplies().removeIf(like -> like.equals(reply.getId()));
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Unliked."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }

    public ResponseEntity<?> deleteReply(String jwt, String unique) {
        try {
            String username = jwtService.extractUsername(jwt);

            Optional<User> optionalUser = userRepository.findByUsername(username);
            Optional<Reply> optionalReply = replyRepository.findByUnique(unique);

            if (optionalUser.isEmpty() || optionalReply.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Error in deleting reply. Please try again."));
            }

            User user = optionalUser.get();
            Reply reply = optionalReply.get();

            Optional<Post> optionalPost = postRepository.findById(reply.getPostId());

            if (optionalPost.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Error in deleting reply. Please try again."));
            }

            Post post = optionalPost.get();

            Boolean isTheUserOwner = reply.getUserId().equals(user.getId());

            if (!isTheUserOwner) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Error in deleting reply. Please try again."));
            }

            user.getReplies().removeIf(usersReplies -> Objects.equals(usersReplies, reply.getId()));
            post.getReplies().removeIf(postReplies -> Objects.equals(postReplies, reply.getId()));

            postRepository.save(post);
            userRepository.save(user);
            replyRepository.delete(reply);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("message", "Reply deleted."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Server error. Please try again."));
        }
    }
}
