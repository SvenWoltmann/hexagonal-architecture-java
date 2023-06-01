package eu.happycoders.shop.application.port.in.cart;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.ProductId;

public interface AddToCartUseCase {

  Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException;
}
