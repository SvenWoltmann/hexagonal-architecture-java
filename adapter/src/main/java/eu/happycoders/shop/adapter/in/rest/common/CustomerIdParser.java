package eu.happycoders.shop.adapter.in.rest.common;

import static eu.happycoders.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;

import eu.happycoders.shop.model.customer.CustomerId;
import org.springframework.http.HttpStatus;

/**
 * A parser for customer IDs, throwing a {@link
 * org.springframework.web.client.HttpClientErrorException} for invalid customer IDs.
 *
 * @author Sven Woltmann
 */
public final class CustomerIdParser {

  private CustomerIdParser() {}

  public static CustomerId parseCustomerId(String string) {
    try {
      return new CustomerId(Integer.parseInt(string));
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'customerId'");
    }
  }
}
