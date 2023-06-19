package eu.happycoders.shop.adapter.in.rest.cart;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public final class CartsControllerAssertions {

  private CartsControllerAssertions() {}

  public static void assertThatResponseIsCart(Response response, Cart cart) {
    assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

    JsonPath json = response.jsonPath();

    for (int i = 0; i < cart.lineItems().size(); i++) {
      CartLineItem lineItem = cart.lineItems().get(i);

      String lineItemPrefix = "lineItems[%d].".formatted(i);

      assertThat(json.getString(lineItemPrefix + "productId"))
          .isEqualTo(lineItem.product().id().value());
      assertThat(json.getString(lineItemPrefix + "productName"))
          .isEqualTo(lineItem.product().name());
      assertThat(json.getString(lineItemPrefix + "price.currency"))
          .isEqualTo(lineItem.product().price().currency().getCurrencyCode());
      assertThat(json.getDouble(lineItemPrefix + "price.amount"))
          .isEqualTo(lineItem.product().price().amount().doubleValue());
      assertThat(json.getInt(lineItemPrefix + "quantity")).isEqualTo(lineItem.quantity());
    }

    assertThat(json.getInt("numberOfItems")).isEqualTo(cart.numberOfItems());

    if (cart.subTotal() != null) {
      assertThat(json.getString("subTotal.currency"))
          .isEqualTo(cart.subTotal().currency().getCurrencyCode());
      assertThat(json.getDouble("subTotal.amount"))
          .isEqualTo(cart.subTotal().amount().doubleValue());
    } else {
      assertThat(json.getString("subTotal")).isNull();
    }
  }
}
