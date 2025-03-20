package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/api")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/auth/reply/create")
    public ResponseEntity<?> createReply(@RequestPart String postUnique, @RequestPart String jwt, @RequestPart(value = "image", required = false) String image, @RequestPart String content) {
        return replyService.createReply(postUnique, jwt, image, content);
    }

    @PostMapping("/auth/reply/like")
    public ResponseEntity<?> likeReply(@RequestPart String jwt, @RequestPart String replyUnique) {
        return replyService.likeReply(jwt, replyUnique);
    }

    @PostMapping("/auth/reply/unlike")
    public ResponseEntity<?> unlikeReply(@RequestPart String jwt, @RequestPart String replyUnique) {
        return replyService.unlikeReply(jwt, replyUnique);
    }
}
