package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import io.quarkus.arc.lookup.LookupIfProperty;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Persistence adapter: Stores carts in memory.
 *
 * @author Sven Woltmann
 */
@LookupIfProperty(name = "persistence", stringValue = "inmemory", lookupIfMissing = true)
@ApplicationScoped
public class InMemoryCartRepository implements CartRepository {

  private final Map<CustomerId, Cart> carts = new ConcurrentHashMap<>();

  @Override
  public void save(Cart cart) {
    carts.put(cart.id(), cart);
  }

  @Override
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    return Optional.ofNullable(carts.get(customerId));
  }

  @Override
  public void deleteByCustomerId(CustomerId customerId) {
    carts.remove(customerId);
  }
}
