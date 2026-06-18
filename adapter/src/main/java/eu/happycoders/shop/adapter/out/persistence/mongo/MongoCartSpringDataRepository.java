package eu.happycoders.shop.adapter.out.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link CartMongoEntity}.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@Repository
public interface MongoCartSpringDataRepository extends MongoRepository<CartMongoEntity, Integer> {}
