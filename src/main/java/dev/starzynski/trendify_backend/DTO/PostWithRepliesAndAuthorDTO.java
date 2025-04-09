package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PostWithRepliesAndAuthorDTO {
    private String unique;
    private String content;
    private Date createdAtDate;
    private Set<ReplyDTO> replies;
    private Set<UserDTO> likes;
    private UserDTO user;
    private Integer countLikes;

    public PostWithRepliesAndAuthorDTO(Post post, Set<Reply> replies, User user, UserRepository userRepository, PostRepository postRepository) {
        this.unique = post.getUnique();
        this.content = post.getContent();
        this.createdAtDate = post.getCreatedAtDate();
        this.replies = replies
                .stream()
                .map(reply -> new ReplyDTO(reply, userRepository, postRepository))
                .collect(Collectors.toSet());
        this.user = new UserDTO(user, postRepository);
        this.countLikes = post.getLikes().size();
        this.likes = post.getLikes().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(opt -> new UserDTO(opt.get(), postRepository))
                .collect(Collectors.toSet());
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

    public Set<UserDTO> getLikes() {
        return likes;
    }

    public Integer getReplyCount() { return replies.size(); }
    public Integer getLikeCount() { return countLikes; }
}
