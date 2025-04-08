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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@Tag(name = "Posts", description = "Post managment API")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/auth/post/create")
    @Operation(summary = "Create post", description = "Returns post unique param")
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String authHeader, @RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        String jwt = authHeader.replace("Bearer ", "");
        return postService.createPost(content, image, jwt);
    }

    /*
    @PostMapping("/auth/post/like/{unique}")
    @Operation(summary = "Like post", description = "Returns message")
    public ResponseEntity<?> likePost(@RequestHeader("Authorization") String authHeader, @PathVariable String unique) {
        String jwt = authHeader.replace("Bearer ", "");
        return postService.likePost(jwt, unique);
    }

    @PostMapping("/auth/post/unlike/{unique}")
    @Operation(summary = "Unlike post", description = "Returns message")
    public ResponseEntity<?> unlikePost(@RequestHeader("Authorization") String authHeader, @PathVariable String unique) {
        String jwt = authHeader.replace("Bearer ", "");
        return postService.unlikePost(jwt, unique);
    }

    @GetMapping("/public/post/getall")
    @Operation(summary = "Get all posts", description = "Returns post objects by newest")
    public ResponseEntity<?> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/public/post/{unique}")
    @Operation(summary = "Get single post by unique", description = "Returns one post object")
    public ResponseEntity<?> getPostByUnique(@PathVariable String unique) {
        return postService.getPostByUnique(unique);
    }

    @DeleteMapping("/auth/post/{unique}")
    @Operation(summary = "Delete post by unique", description = "Returns message")
    public ResponseEntity<?> deletePost(@RequestHeader("Authorization") String authHeader, @PathVariable String unique) {
        String jwt = authHeader.replace("Bearer ", "");
        return postService.deletePost(jwt, unique);
    } */
}
