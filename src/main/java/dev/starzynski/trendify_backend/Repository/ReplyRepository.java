package dev.starzynski.trendify_backend.Repository;

import dev.starzynski.trendify_backend.Model.Reply;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, ObjectId> {
}
