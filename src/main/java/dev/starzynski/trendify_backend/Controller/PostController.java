package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/auth/post/create")
    public ResponseEntity<?> createPost(@RequestPart String jwt, @RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        return postService.createPost(content, image, jwt);
    }

    @PostMapping("/auth/post/like")
    public ResponseEntity<?> likePost(@RequestPart String jwt, @RequestPart String postUnique) {
        return postService.likePost(jwt, postUnique);
    }
}
