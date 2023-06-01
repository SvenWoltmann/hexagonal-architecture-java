package eu.happycoders.shop.model.customer;

/**
 * A customer ID value object (enabling type-safety and validation).
 *
 * @author Sven Woltmann
 */
public record CustomerId(int value) {

  public CustomerId {
    if (value < 1) {
      throw new IllegalArgumentException("'value' must be a positive integer");
    }
  }
}
