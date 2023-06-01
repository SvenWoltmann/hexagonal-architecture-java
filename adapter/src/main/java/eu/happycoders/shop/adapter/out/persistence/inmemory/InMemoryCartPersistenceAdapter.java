package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCartPersistenceAdapter implements CartPersistencePort {

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
  public void deleteById(CustomerId customerId) {
    carts.remove(customerId);
  }
}
