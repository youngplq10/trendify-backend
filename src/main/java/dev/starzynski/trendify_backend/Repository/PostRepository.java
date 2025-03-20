package dev.starzynski.trendify_backend.Repository;

import dev.starzynski.trendify_backend.Model.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId> {
}
