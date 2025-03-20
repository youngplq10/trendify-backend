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

    public ResponseEntity<?> createReply(String postUnique, String jwt, String image, String content) {
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
            reply.setUser(optionalUser.get());
            reply.setPost(optionalPost.get());

            if (image != null) {
                reply.setImageLink(image);
            }

            replyRepository.insert(reply);

            optionalUser.get().getReplies().add(reply);
            userRepository.save(optionalUser.get());

            optionalPost.get().getReplies().add(reply);
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
}
