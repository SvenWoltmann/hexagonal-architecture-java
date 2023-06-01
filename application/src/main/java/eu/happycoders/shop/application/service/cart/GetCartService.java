package eu.happycoders.shop.application.service.cart;

import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Objects;

public class GetCartService implements GetCartUseCase {

  private final CartPersistencePort cartPersistencePort;

  public GetCartService(CartPersistencePort cartPersistencePort) {
    this.cartPersistencePort = cartPersistencePort;
  }

  @Override
  public Cart getCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    return cartPersistencePort.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
  }
}
