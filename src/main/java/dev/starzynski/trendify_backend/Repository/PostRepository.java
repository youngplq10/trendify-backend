package dev.starzynski.trendify_backend.Repository;

import dev.starzynski.trendify_backend.Model.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId> {
    Optional<Post> findByUnique(String postUnique);
    List<Post> findAllByOrderByCreatedAtDateDesc();
}
