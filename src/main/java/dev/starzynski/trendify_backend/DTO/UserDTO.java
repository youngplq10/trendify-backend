package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.User;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Set;

public class UserDTO {
    private String username, email, profilePicture, bio;

    private Boolean newsletter;

    private Date createdAtDate;

    private Set<ObjectId> posts;

    private Set<ObjectId> replies;

    private Set<ObjectId> followers;

    private Set<ObjectId> following;

    private Set<ObjectId> likedPosts;

    private Set<ObjectId> likedReplies;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.bio = user.getBio();
        this.newsletter = user.getNewsletter();
        this.createdAtDate = user.getCreatedAtDate();
        this.followers = user.getFollowers();
        this.following = user.getFollowing();
        this.posts = user.getPosts();
        this.replies = user.getReplies();
    }
}
