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
}
