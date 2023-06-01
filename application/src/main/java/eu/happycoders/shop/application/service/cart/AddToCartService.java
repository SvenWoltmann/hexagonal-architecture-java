package eu.happycoders.shop.application.service.cart;

import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.ProductNotFoundException;
import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {

  private final CartPersistencePort cartPersistencePort;
  private final ProductPersistencePort productPersistencePort;

  public AddToCartService(
      CartPersistencePort cartPersistencePort, ProductPersistencePort productPersistencePort) {
    this.cartPersistencePort = cartPersistencePort;
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  public Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    Objects.requireNonNull(productId, "'productId' must not be null");
    if (quantity < 1) {
      throw new IllegalArgumentException("'quantity' must be greater than 0");
    }

    Product product =
        productPersistencePort.findById(productId).orElseThrow(ProductNotFoundException::new);

    Cart cart =
        cartPersistencePort.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));

    product.addToCart(cart, quantity);

    cartPersistencePort.save(cart);

    return cart;
  }
}
