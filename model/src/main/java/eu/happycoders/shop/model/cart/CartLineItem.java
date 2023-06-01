package eu.happycoders.shop.model.cart;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

/**
 * A shopping cart line item with a product and quantity.
 *
 * @author Sven Woltmann
 */
public class CartLineItem {

  private final Product product;
  private int quantity;

  public CartLineItem(Product product) {
    this.product = product;
  }

  CartLineItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product product() {
    return product;
  }

  public int quantity() {
    return quantity;
  }

  public void increaseQuantityBy(int augend, int itemsInStock)
      throws NotEnoughItemsInStockException {
    if (augend < 1) {
      throw new IllegalArgumentException("You must add at least one item");
    }

    int newQuantity = quantity + augend;
    if (itemsInStock < newQuantity) {
      throw new NotEnoughItemsInStockException(
          "Product %s has less items in stock (%d) than the requested total quantity (%d)"
              .formatted(product.id(), product.itemsInStock(), newQuantity),
          product.itemsInStock());
    }

    this.quantity = newQuantity;
  }

  public Money subTotal() {
    return product.price().multiply(quantity);
  }
}
