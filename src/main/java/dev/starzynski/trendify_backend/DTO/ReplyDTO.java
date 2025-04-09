package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Post;
import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.ReplyRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ReplyDTO {
    private String imageLink, content, unique;

    private Date createdAtDate;

    private UserDTO user;

    private Set<UserDTO> likes;

    public ReplyDTO(Reply reply, UserRepository userRepository, PostRepository postRepository, ReplyRepository replyRepository) {
        this.imageLink = reply.getImageLink();
        this.content = reply.getContent();
        this.unique = reply.getUnique();
        this.createdAtDate = reply.getCreatedAtDate();

        Optional<User> optionalUser  = userRepository.findById(reply.getUserId());

        optionalUser.ifPresent(value -> this.user = new UserDTO(value, postRepository, replyRepository, userRepository));

        this.likes = reply.getLikes().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(opt -> new UserDTO(opt.get(), postRepository, replyRepository, userRepository))
                .collect(Collectors.toSet());
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getContent() {
        return content;
    }

    public String getUnique() {
        return unique;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public Set<UserDTO> getLikes() {
        return likes;
    }

    public Integer getCountLikes() { return likes.size(); }
}
