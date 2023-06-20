package eu.happycoders.shop.application.service.cart;

import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Objects;

/**
 * Use case implementation: Retrieving a shopping cart.
 *
 * @author Sven Woltmann
 */
public class GetCartService implements GetCartUseCase {

  private final CartRepository cartRepository;

  public GetCartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public Cart getCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    return cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
  }
}
