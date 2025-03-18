package dev.starzynski.trendify_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;

    private String username, email, password, profilePicture, role;

    private Boolean newsletter;

    private Date createdAtDate;

    @DBRef
    @JsonIgnoreProperties("user")
    private List<Post> posts;

    @DBRef
    @JsonIgnoreProperties({"posts", "replies", "followers", "following", "likes"})
    private List<User> followers;

    @DBRef
    @JsonIgnoreProperties({"posts", "replies", "followers", "following", "likes"})
    private List<User> following;

    @DBRef
    @JsonIgnoreProperties({"replies"})
    private List<Post> likes;

    @DBRef
    private List<Reply> replies;

    public User() {
        this.id = new ObjectId();
        this.newsletter = false;
        this.role = "USER";
        this.createdAtDate = new Date();
        this.posts = new ArrayList<>();
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

    public List<Post> getPosts() { return posts; }
}
