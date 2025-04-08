package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Reply;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Set;

public class ReplyDTO {
    private String imageLink, content, unique;

    private Date createdAtDate;

    private ObjectId userId;

    private ObjectId postId;

    private Set<ObjectId> likes;

    public ReplyDTO(Reply reply) {
        this.imageLink = reply.getImageLink();
        this.content = reply.getContent();
        this.unique = reply.getUnique();
        this.createdAtDate = reply.getCreatedAtDate();
        this.userId = reply.getUserId();
        this.postId = reply.getPostId();
        this.likes = reply.getLikes();
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

    public ObjectId getPostId() {
        return postId;
    }

    public Set<ObjectId> getLikes() {
        return likes;
    }

}
