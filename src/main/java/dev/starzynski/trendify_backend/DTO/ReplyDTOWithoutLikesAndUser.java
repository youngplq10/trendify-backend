package dev.starzynski.trendify_backend.DTO;

import dev.starzynski.trendify_backend.Model.Reply;

import java.util.Date;

public class ReplyDTOWithoutLikesAndUser {
    private String imageLink, content, unique;

    private Date createdAtDate;

    public ReplyDTOWithoutLikesAndUser(Reply reply) {
        this.imageLink = reply.getImageLink();
        this.content = reply.getContent();
        this.unique = reply.getUnique();
        this.createdAtDate = reply.getCreatedAtDate();
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
}
