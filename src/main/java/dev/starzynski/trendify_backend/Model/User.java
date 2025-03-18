package dev.starzynski.trendify_backend.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;

    private String username, email, password, profilePicture;

    private Boolean newsletter;

    public User() {
        this.id = new ObjectId();
        this.newsletter = false;
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

    public Boolean getNewsletter() { return newsletter; }
    public void setNewsletter(Boolean newsletter) { this.newsletter = newsletter; }
}
