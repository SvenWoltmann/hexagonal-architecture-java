package eu.happycoders.shop.adapter.out.persistence.mongo;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.customer.CustomerId;

/**
 * Maps model carts and line items to MongoDB carts and line items - and vice versa.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
final class CartMapper {

  private CartMapper() {}

  static CartMongoEntity toMongoEntity(Cart cart) {
    CartMongoEntity cartMongoEntity = new CartMongoEntity();
    cartMongoEntity.setCustomerId(cart.id().value());

    cartMongoEntity.setLineItems(
        cart.lineItems().stream()
            .map(lineItem -> toMongoEntity(cartMongoEntity, lineItem))
            .toList());

    return cartMongoEntity;
  }

  static CartLineItemMongoEntity toMongoEntity(
      CartMongoEntity cartMongoEntity, CartLineItem lineItem) {
    ProductMongoEntity productMongoEntity = new ProductMongoEntity();
    productMongoEntity.setId(lineItem.product().id().value());

    CartLineItemMongoEntity entity = new CartLineItemMongoEntity();
    entity.setProduct(productMongoEntity);
    entity.setQuantity(lineItem.quantity());

    return entity;
  }

  static Cart toModelEntity(CartMongoEntity cartMongoEntity) {
    CustomerId customerId = new CustomerId(cartMongoEntity.getCustomerId());
    Cart cart = new Cart(customerId);

    for (CartLineItemMongoEntity lineItemMongoEntity : cartMongoEntity.getLineItems()) {
      cart.putProductIgnoringNotEnoughItemsInStock(
          ProductMapper.toModelEntity(lineItemMongoEntity.getProduct()),
          lineItemMongoEntity.getQuantity());
    }

    return cart;
  }
}
