package eu.happycoders.shop.adapter.out.persistence.mongo;

import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence adapter: Stores carts via MongoDB in a database.
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@ConditionalOnProperty(name = "persistence", havingValue = "mongodb")
@Repository
public class MongoCartRepository implements CartRepository {

  private final MongoCartSpringDataRepository mongoCartSpringDataRepository;

  public MongoCartRepository(
      MongoCartSpringDataRepository mongoCartSpringDataRepository) {
    this.mongoCartSpringDataRepository = mongoCartSpringDataRepository;
  }

  @Override
  @Transactional
  public void save(Cart cart) {
    mongoCartSpringDataRepository.save(CartMapper.toMongoEntity(cart));
  }

  @Override
  @Transactional
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    Optional<CartMongoEntity> cartMongoEntity =
        mongoCartSpringDataRepository.findById(customerId.value());
    return cartMongoEntity.map(CartMapper::toModelEntity);
  }

  @Override
  @Transactional
  public void deleteByCustomerId(CustomerId customerId) {
    mongoCartSpringDataRepository.deleteById(customerId.value());
  }
}
