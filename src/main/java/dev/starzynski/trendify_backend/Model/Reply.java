package dev.starzynski.trendify_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.starzynski.trendify_backend.Service.GenerateRandomStringService;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "replies")
public class Reply {
    @Id
    private ObjectId id;

    private String imageLink, content, unique;

    private Date createdAtDate;

    @DBRef
    @JsonIgnoreProperties({"posts", "replies", "followers", "following", "likes"})
    private User user;

    @DBRef
    @JsonIgnoreProperties({"likes", "replies"})
    private Post post;

    public Reply() {
        this.id = new ObjectId();
        this.createdAtDate = new Date();

        GenerateRandomStringService generateRandomStringService = new GenerateRandomStringService();
        this.unique = generateRandomStringService.generateRandom(15);
    }

    public ObjectId getId() { return id; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUnique() { return unique; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public Date getCreatedAtDate() { return createdAtDate; }
}
