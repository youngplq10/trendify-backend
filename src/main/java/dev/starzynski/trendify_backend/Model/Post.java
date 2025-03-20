package dev.starzynski.trendify_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.starzynski.trendify_backend.Service.GenerateRandomStringService;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "posts")
public class Post {
    @Id
    private ObjectId id;

    private String imageLink, content, unique;

    private Date createdAtDate;

    @DBRef
    @JsonIgnoreProperties({"posts", "replies", "followers", "following", "likes"})
    private User user;

    @DBRef
    @JsonIgnoreProperties("post")
    private List<Reply> replies;

    @DBRef
    @JsonIgnoreProperties
    private Set<User> likes;

    public Post() {
        this.id = new ObjectId();
        this.createdAtDate = new Date();

        this.replies = new ArrayList<>();
        this.likes = new HashSet<>();

        GenerateRandomStringService generateRandomStringService = new GenerateRandomStringService();
        this.unique = generateRandomStringService.generateRandom(15);
    }

    public ObjectId getId() { return id; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUnique() { return unique; }

    public Date getCreatedAtDate() { return createdAtDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Reply> getReplies() { return replies; }
    public Set<User> getLikes() { return likes; }
}
