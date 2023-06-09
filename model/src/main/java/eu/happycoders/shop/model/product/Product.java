package eu.happycoders.shop.model.product;

import eu.happycoders.shop.model.money.Money;
import lombok.EqualsAndHashCode;

/**
 * A product listed in the shop.
 *
 * @author Sven Woltmann
 */
@SuppressWarnings("PMD.ImmutableField") // name, description, etc... should eventually be modifiable
@EqualsAndHashCode
public class Product {

  private final ProductId id;
  private String name;
  private String description;
  private Money price;
  private int itemsInStock;

  public Product(ProductId id, String name, String description, Money price, int itemsInStock) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.itemsInStock = itemsInStock;
  }

  public ProductId id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public Money price() {
    return price;
  }

  public int itemsInStock() {
    return itemsInStock;
  }
}
