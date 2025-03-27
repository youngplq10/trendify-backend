package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/api")
@Tag(name = "Replies", description = "Reply managment API")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/auth/reply/create")
    @Operation(summary = "Create reply", description = "Returns reply unique param")
    public ResponseEntity<?> createReply(@RequestPart String postUnique, @RequestHeader("Authorization") String authHeader, @RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.createReply(postUnique, jwt, image, content);
    }

    @PostMapping("/auth/reply/like")
    @Operation(summary = "Like reply", description = "Returns message")
    public ResponseEntity<?> likeReply(@RequestHeader("Authorization") String authHeader, @RequestPart String replyUnique) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.likeReply(jwt, replyUnique);
    }

    @PostMapping("/auth/reply/unlike")dd
    @Operation(summary = "Unlike reply", description = "Returns message")
    public ResponseEntity<?> unlikeReply(@RequestHeader("Authorization") String authHeader, @RequestPart String replyUnique) {
        String jwt = authHeader.replace("Bearer ", "");
        return replyService.unlikeReply(jwt, replyUnique);
    }
}
