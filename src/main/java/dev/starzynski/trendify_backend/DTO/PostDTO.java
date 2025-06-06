package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Post;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Set;

public class PostDTO {
    private String imageLink, content, unique;

    private Date createdAtDate;

    private ObjectId userId;

    private Set<ObjectId> replies;

    private Set<ObjectId> likes;

    public PostDTO(Post post) {
        this.imageLink = post.getImageLink();
        this.content = post.getContent();
        this.unique = post.getUnique();
        this.createdAtDate = post.getCreatedAtDate();
        this.userId = post.getUserId();
        this.replies = post.getReplies();
        this.likes = post.getLikes();
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

    public ObjectId getUserId() {
        return userId;
    }

    public Set<ObjectId> getReplies() {
        return replies;
    }

    public Set<ObjectId> getLikes() {
        return likes;
    }

    public Integer getLikeCount() { return likes.size(); }

    public Integer getReplyCount() { return replies.size(); }
}
