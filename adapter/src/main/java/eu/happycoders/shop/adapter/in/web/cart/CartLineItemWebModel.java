package eu.happycoders.shop.adapter.in.web.cart;

import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

public record CartLineItemWebModel(
    String productId, String productName, Money price, int quantity) {

  public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
    Product product = lineItem.product();
    return new CartLineItemWebModel(
        product.id().value(), product.name(), product.price(), lineItem.quantity());
  }
}
