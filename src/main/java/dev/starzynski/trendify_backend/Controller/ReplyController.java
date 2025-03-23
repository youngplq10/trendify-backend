package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> createReply(@RequestPart String postUnique, @RequestPart String jwt, @RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        return replyService.createReply(postUnique, jwt, image, content);
    }

    @PostMapping("/auth/reply/like")
    @Operation(summary = "Like reply", description = "Returns message")
    public ResponseEntity<?> likeReply(@RequestPart String jwt, @RequestPart String replyUnique) {
        return replyService.likeReply(jwt, replyUnique);
    }

    @PostMapping("/auth/reply/unlike")
    @Operation(summary = "Unlike reply", description = "Returns message")
    public ResponseEntity<?> unlikeReply(@RequestPart String jwt, @RequestPart String replyUnique) {
        return replyService.unlikeReply(jwt, replyUnique);
    }
}
