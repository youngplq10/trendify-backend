package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@Tag(name = "Replies", description = "Reply managment API")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/auth/reply/create")
    @Operation(summary = "Create reply", description = "Returns reply unique param")
    public ResponseEntity<?> createReply(@RequestPart String postUnique, @RequestHeader("Authorization") String authHeader, @RequestPart(value = "imageLink", required = false) String imageLink, @RequestPart String content) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.createReply(postUnique, jwt, imageLink, content);
    }

    @PostMapping("/auth/reply/like/{unique}")
    @Operation(summary = "Like reply", description = "Returns message")
    public ResponseEntity<?> likeReply(@RequestHeader("Authorization") String authHeader, @PathVariable String unique) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.likeReply(jwt, unique);
    }

    @PostMapping("/auth/reply/unlike/{unique}")
    @Operation(summary = "Unlike reply", description = "Returns message")
    public ResponseEntity<?> unlikeReply(@RequestHeader("Authorization") String authHeader, @PathVariable String unique) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.unlikeReply(jwt, unique);
    }
}
