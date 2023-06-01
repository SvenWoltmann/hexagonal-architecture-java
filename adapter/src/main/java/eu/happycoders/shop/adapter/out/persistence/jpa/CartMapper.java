package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.customer.CustomerId;
import java.util.Optional;

final class CartMapper {

  private CartMapper() {}

  static CartJpaEntity toJpaEntity(Cart cart) {
    CartJpaEntity cartJpaEntity = new CartJpaEntity();
    cartJpaEntity.setCustomerId(cart.id().value());

    cartJpaEntity.setLineItems(
        cart.lineItems().stream()
            .map(lineItem -> toCartLineItemJpaEntity(cartJpaEntity, lineItem))
            .toList());

    return cartJpaEntity;
  }

  static CartLineItemJpaEntity toCartLineItemJpaEntity(
      CartJpaEntity cartJpaEntity, CartLineItem lineItem) {
    ProductJpaEntity productJpaEntity = new ProductJpaEntity();
    productJpaEntity.setId(lineItem.product().id().value());

    CartLineItemJpaEntity entity = new CartLineItemJpaEntity();
    entity.setCart(cartJpaEntity);
    entity.setProduct(productJpaEntity);
    entity.setQuantity(lineItem.quantity());

    return entity;
  }

  static Optional<Cart> toCartModelEntity(CartJpaEntity cartJpaEntity) {
    if (cartJpaEntity == null) {
      return Optional.empty();
    }

    CustomerId customerId = new CustomerId(cartJpaEntity.getCustomerId());
    Cart cart = new Cart(customerId);

    for (CartLineItemJpaEntity lineItemEntity : cartJpaEntity.getLineItems()) {
      cart.putProductIgnoringNotEnoughItemsInStock(
          ProductMapper.toModelEntity(lineItemEntity.getProduct()), lineItemEntity.getQuantity());
    }

    return Optional.of(cart);
  }
}
