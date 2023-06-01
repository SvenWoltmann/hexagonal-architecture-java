package eu.happycoders.shop.application.port.in.cart;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;

public interface GetCartUseCase {

  Cart getCart(CustomerId customerId);
}
