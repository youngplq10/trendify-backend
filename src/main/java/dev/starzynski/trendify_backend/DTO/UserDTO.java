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

    public Set<ObjectId> getPosts() {
        return posts;
    }

    public Set<ObjectId> getReplies() {
        return replies;
    }

    public Set<ObjectId> getFollowers() {
        return followers;
    }

    public Set<ObjectId> getFollowing() {
        return following;
    }

    public Set<ObjectId> getLikedPosts() {
        return likedPosts;
    }

    public Set<ObjectId> getLikedReplies() {
        return likedReplies;
    }
}
