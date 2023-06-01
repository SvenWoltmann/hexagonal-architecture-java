package eu.happycoders.shop.application.service.cart;

import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Objects;

/**
 * Use case implementation: Emptying a shopping cart.
 *
 * @author Sven Woltmann
 */
public class EmptyCartService implements EmptyCartUseCase {

  private final CartPersistencePort cartPersistencePort;

  public EmptyCartService(CartPersistencePort cartPersistencePort) {
    this.cartPersistencePort = cartPersistencePort;
  }

  @Override
  public void emptyCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    cartPersistencePort.deleteById(customerId);
  }
}
