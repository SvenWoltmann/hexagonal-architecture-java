package eu.happycoders.shop.application.port.in.cart;

import eu.happycoders.shop.model.customer.CustomerId;

/**
 * Use case: Emptying a shopping cart.
 *
 * @author Sven Woltmann
 */
public interface EmptyCartUseCase {

  void emptyCart(CustomerId customerId);
}
