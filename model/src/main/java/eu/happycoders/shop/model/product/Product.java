package eu.happycoders.shop.model.product;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.money.Money;

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

  public void addToCart(Cart cart, int quantity) throws NotEnoughItemsInStockException {
    cart.addProduct(this, quantity);
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
