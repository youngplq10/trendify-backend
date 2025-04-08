package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class PostWithRepliesAndAuthorDTO {
    private String unique;
    private String content;
    private Set<ReplyDTO> replies;
    private UserDTO user;

    public PostWithRepliesAndAuthorDTO(Post post, Set<Reply> replies, User user) {
        this.unique = post.getUnique();
        this.content = post.getContent();
        this.replies = replies
                .stream()
                .map(ReplyDTO::new)
                .collect(Collectors.toSet());
        this.user = new UserDTO(user);
    }

    public String getUnique() {
        return unique;
    }

    public String getContent() {
        return content;
    }

    public Set<ReplyDTO> getReplies() {
        return replies;
    }

    public UserDTO getUser() {
        return user;
    }
}
