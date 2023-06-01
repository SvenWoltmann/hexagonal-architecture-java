package eu.happycoders.shop.adapter.in.web.product;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

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
