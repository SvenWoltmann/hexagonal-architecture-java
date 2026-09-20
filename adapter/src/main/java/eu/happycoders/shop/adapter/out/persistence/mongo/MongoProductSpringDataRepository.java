package eu.happycoders.shop.adapter.out.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for {@link ProductMongoEntity}.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@Repository
public interface MongoProductSpringDataRepository
    extends MongoRepository<ProductMongoEntity, String> {

  @Query("{ '$or': [ { 'name': { '$regex': ?0, '$options': 'i' } }, { 'description': { '$regex': ?0, '$options': 'i' } } ] }")
  List<ProductMongoEntity> findByNameOrDescriptionLike(String pattern);

}
