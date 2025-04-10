package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Reply;
import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Repository.PostRepository;
import dev.starzynski.trendify_backend.Repository.ReplyRepository;
import dev.starzynski.trendify_backend.Repository.UserRepository;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private String username, email, profilePicture, bio;

    private Boolean newsletter;

    private Date createdAtDate;

    private Set<PostDTO> posts;

    private Set<ReplyDTOWithoutLikesAndUser> replies;

    private Set<PostDTO> likedPosts;

    private Set<ReplyDTOWithoutLikesAndUser> likedReplies;

    private Set<UserDTO> followers;

    private Set<UserDTO> following;

    public UserDTO(User user, PostRepository postRepository, ReplyRepository replyRepository, UserRepository userRepository) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.bio = user.getBio();
        this.newsletter = user.getNewsletter();
        this.createdAtDate = user.getCreatedAtDate();

        this.posts = user.getPosts().stream()
                .map(postRepository::findById)
                .filter(Optional::isPresent)
                .map(post -> new PostDTO(post.get()))
                .collect(Collectors.toSet());

        this.likedPosts = user.getLikedPosts().stream()
                .map(postRepository::findById)
                .filter(Optional::isPresent)
                .map(post -> new PostDTO(post.get()))
                .collect(Collectors.toSet());

        this.replies = user.getReplies().stream()
                .map(replyRepository::findById)
                .filter(Optional::isPresent)
                .map(reply -> new ReplyDTOWithoutLikesAndUser(reply.get()))
                .collect(Collectors.toSet());

        this.likedReplies = user.getLikedReplies().stream()
                .map(replyRepository::findById)
                .filter(Optional::isPresent)
                .map(reply -> new ReplyDTOWithoutLikesAndUser(reply.get()))
                .collect(Collectors.toSet());

        this.followers = user.getFollowers().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(optUser -> new UserDTO(optUser.get(), postRepository, replyRepository, userRepository))
                .collect(Collectors.toSet());

        this.following = user.getFollowing().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(optUser -> new UserDTO(optUser.get(), postRepository, replyRepository, userRepository))
                .collect(Collectors.toSet());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public Set<PostDTO> getPosts() {
        return posts;
    }

    public Set<ReplyDTOWithoutLikesAndUser> getReplies() {
        return replies;
    }

    public Set<PostDTO> getLikedPosts() {
        return likedPosts;
    }

    public Set<ReplyDTOWithoutLikesAndUser> getLikedReplies() {
        return likedReplies;
    }

    public Integer getFollowersCount() { return followers.size(); }

    public Integer getFollowingCount() { return following.size(); }
}
