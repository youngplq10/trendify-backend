package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class PostWithRepliesAndAuthorDTO {
    private String unique;
    private String content;
    private Date createdAtDate;
    private Set<ReplyDTO> replies;
    private UserDTO user;

    public PostWithRepliesAndAuthorDTO(Post post, Set<Reply> replies, User user, UserRepository userRepository) {
        this.unique = post.getUnique();
        this.content = post.getContent();
        this.createdAtDate = post.getCreatedAtDate();
        this.replies = replies
                .stream()
                .map(reply -> new ReplyDTO(reply, userRepository))
                .collect(Collectors.toSet());
        this.user = new UserDTO(user);
    }

    public String getUnique() {
        return unique;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public Set<ReplyDTO> getReplies() {
        return replies;
    }

    public UserDTO getUser() {
        return user;
    }
}
