package eu.happycoders.shop.application.port.in.cart;

import eu.happycoders.shop.model.customer.CustomerId;

public interface EmptyCartUseCase {

  void emptyCart(CustomerId customerId);
}
