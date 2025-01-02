package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;

/**
 * Persistence adapter: Stores carts via JPA in a database.
 *
 * @author Sven Woltmann
 */
public class JpaCartRepository implements CartRepository {

  private final EntityManagerFactory entityManagerFactory;

  public JpaCartRepository(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void save(Cart cart) {
    try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
      entityManager.getTransaction().begin();
      entityManager.merge(CartMapper.toJpaEntity(cart));
      entityManager.getTransaction().commit();
    }
  }

  @Override
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
      CartJpaEntity cartJpaEntity = entityManager.find(CartJpaEntity.class, customerId.value());
      return CartMapper.toModelEntityOptional(cartJpaEntity);
    }
  }

  @Override
  public void deleteByCustomerId(CustomerId customerId) {
    try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
      entityManager.getTransaction().begin();

      CartJpaEntity cartJpaEntity = entityManager.find(CartJpaEntity.class, customerId.value());

      if (cartJpaEntity != null) {
        entityManager.remove(cartJpaEntity);
      }

      entityManager.getTransaction().commit();
    }
  }
}
