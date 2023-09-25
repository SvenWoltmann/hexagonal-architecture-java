package eu.happycoders.shop.model.product;

import eu.happycoders.shop.model.money.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A product listed in the shop.
 *
 * @author Sven Woltmann
 */
@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Product {

  private final ProductId id;
  private String name;
  private String description;
  private Money price;
  private int itemsInStock;
}
