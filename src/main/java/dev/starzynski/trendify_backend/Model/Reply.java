package dev.starzynski.trendify_backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.starzynski.trendify_backend.Service.GenerateRandomStringService;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "replies")
public class Reply {
    @Id
    private ObjectId id;

    private String imageLink, content, unique;

    private Date createdAtDate;

    private ObjectId userId;

    private ObjectId postId;

    private Set<ObjectId> likes;

    public Reply() {
        this.id = new ObjectId();
        this.createdAtDate = new Date();
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

    public ObjectId getUserId() { return userId; }
    public void setUserId(ObjectId userId) { this.userId = userId; }

    public ObjectId getPostId() { return postId; }
    public void setPostId(ObjectId postId) { this.postId = postId; }

    public Date getCreatedAtDate() { return createdAtDate; }

    public Set<ObjectId> getLikes() { return likes; }

    public Integer getCountLikes() { return likes.size(); }
}
