package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/api")
@Tag(name = "Posts", description = "Post managment API")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/auth/post/create")
    @Operation(summary = "Create post", description = "Returns post unique param")
    public ResponseEntity<?> createPost(@RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return postService.createPost(content, image, jwt);
    }

    @PostMapping("/auth/post/like")
    @Operation(summary = "Like post", description = "Returns message")
    public ResponseEntity<?> likePost(@RequestPart String postUnique) {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return postService.likePost(jwt, postUnique);
    }

    @PostMapping("/auth/post/unlike")
    @Operation(summary = "Unlike post", description = "Returns message")
    public ResponseEntity<?> unlikePost(@RequestPart String postUnique) {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return postService.unlikePost(jwt, postUnique);
    }
}
