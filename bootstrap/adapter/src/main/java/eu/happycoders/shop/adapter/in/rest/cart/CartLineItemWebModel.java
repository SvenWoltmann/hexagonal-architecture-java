package eu.happycoders.shop.adapter.in.rest.cart;

import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

/**
 * Model class for returning a shopping cart line item via REST API.
 *
 * @author Sven Woltmann
 */
public record CartLineItemWebModel(
    String productId, String productName, Money price, int quantity) {

  public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
    Product product = lineItem.product();
    return new CartLineItemWebModel(
        product.id().value(), product.name(), product.price(), lineItem.quantity());
  }
}
