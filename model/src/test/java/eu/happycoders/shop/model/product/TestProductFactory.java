package eu.happycoders.shop.model.product;

import eu.happycoders.shop.model.money.Money;

public class TestProductFactory {

  private static final int ENOUGH_ITEMS_IN_STOCK = Integer.MAX_VALUE;

  public static Product createTestProduct(Money price) {
    return createTestProduct(price, ENOUGH_ITEMS_IN_STOCK);
  }

  public static Product createTestProduct(Money price, int itemsInStock) {
    return new Product(
        ProductId.randomProductId(), //
        "any name",
        "any description",
        price,
        itemsInStock);
  }
}
