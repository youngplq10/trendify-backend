package dev.starzynski.trendify_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.starzynski.trendify_backend.Service.GenerateRandomStringService;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;

    private String username, email, password, profilePicture, role, unique, bio;

    private Boolean newsletter;

    private Date createdAtDate;

    @DBRef
    @JsonIgnoreProperties("user")
    private List<Post> posts;

    @DBRef
    @JsonIgnoreProperties({"posts", "likedPosts", "likedReplies", "replies", "followers", "following", "likes", "email", "password", "role", "unique", "id", "newsletter", "createdAtDate"})
    private Set<User> followers;

    @DBRef
    @JsonIgnoreProperties({"posts", "likedPosts", "likedReplies", "replies", "followers", "following", "likes", "email", "password", "role", "unique", "id", "newsletter", "createdAtDate"})
    private Set<User> following;

    @DBRef
    @JsonIgnoreProperties({"id", "replies", "likes"})
    private Set<Post> likedPosts;

    @DBRef
    @JsonIgnoreProperties({"user", "likes", "id"})
    private List<Reply> likedReplies;

    @DBRef
    @JsonIgnoreProperties({"id", "user", "likes"})
    private List<Reply> replies;

    public User() {
        this.id = new ObjectId();
        this.newsletter = false;
        this.role = "USER";
        this.createdAtDate = new Date();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.posts = new ArrayList<>();
        this.replies = new ArrayList<>();
        this.likedPosts = new HashSet<>();
        this.likedReplies = new ArrayList<>();

        GenerateRandomStringService generateRandomStringService = new GenerateRandomStringService();
        this.unique = generateRandomStringService.generateRandom(15);
    }

    public ObjectId getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getRole() { return role; }

    public Boolean getNewsletter() { return newsletter; }
    public void setNewsletter(Boolean newsletter) { this.newsletter = newsletter; }

    public Date getCreatedAtDate() { return createdAtDate; }

    public Set<User> getFollowers() { return followers; }

    public Set<User> getFollowing() { return following; }

    public List<Post> getPosts() { return posts; }

    public List<Reply> getReplies() { return replies; }

    public Set<Post> getLikedPosts() { return likedPosts; }

    public List<Reply> getLikedReplies() { return likedReplies; }

    public String getUnique() { return unique; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
