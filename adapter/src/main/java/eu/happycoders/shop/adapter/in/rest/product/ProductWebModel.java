package eu.happycoders.shop.adapter.in.rest.product;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

/**
 * Model class for returning a product via REST API.
 *
 * @author Sven Woltmann
 */
public record ProductWebModel(
    String id, String name, String description, Money price, int itemsInStock) {

  public static ProductWebModel fromDomainModel(Product product) {
    return new ProductWebModel(
        product.id().value(),
        product.name(),
        product.description(),
        product.price(),
        product.itemsInStock());
  }
}
