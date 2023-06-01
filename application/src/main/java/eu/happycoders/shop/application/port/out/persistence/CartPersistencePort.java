package eu.happycoders.shop.application.port.out.persistence;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Optional;

public interface CartPersistencePort {

  void save(Cart cart);

  Optional<Cart> findByCustomerId(CustomerId customerId);

  void deleteById(CustomerId customerId);
}
