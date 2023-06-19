package eu.happycoders.shop.bootstrap.e2e;

import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.TEST_PORT;
import static eu.happycoders.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.LED_LIGHTS;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.MONITOR_DESK_MOUNT;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartTest extends EndToEndTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final String CARTS_PATH = "/carts/" + TEST_CUSTOMER_ID.value();

  @Test
  @Order(1)
  void givenAnEmptyCart_addLineItem_addsTheLineItemAndReturnsTheCartWithTheAddedItem()
      throws NotEnoughItemsInStockException {
    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", LED_LIGHTS.id().value())
            .queryParam("quantity", 3)
            .post(CARTS_PATH + "/line-items")
            .then()
            .extract()
            .response();

    Cart expectedCart = new Cart(TEST_CUSTOMER_ID);
    expectedCart.addProduct(LED_LIGHTS, 3);

    assertThatResponseIsCart(response, expectedCart);
  }

  @Test
  @Order(2)
  void givenACartWithOneLineItem_addLineItem_addsTheLineItemAndReturnsACartWithTwoLineItems()
      throws NotEnoughItemsInStockException {
    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", MONITOR_DESK_MOUNT.id().value())
            .queryParam("quantity", 1)
            .post(CARTS_PATH + "/line-items")
            .then()
            .extract()
            .response();

    Cart expectedCart = new Cart(TEST_CUSTOMER_ID);
    expectedCart.addProduct(LED_LIGHTS, 3);
    expectedCart.addProduct(MONITOR_DESK_MOUNT, 1);

    assertThatResponseIsCart(response, expectedCart);
  }

  @Test
  @Order(3)
  void givenACartWithTwoLineItems_getCart_returnsTheCart() throws NotEnoughItemsInStockException {
    Response response = given().port(TEST_PORT).get(CARTS_PATH).then().extract().response();

    Cart expectedCart = new Cart(TEST_CUSTOMER_ID);
    expectedCart.addProduct(LED_LIGHTS, 3);
    expectedCart.addProduct(MONITOR_DESK_MOUNT, 1);

    assertThatResponseIsCart(response, expectedCart);
  }

  @Test
  @Order(4)
  void givenACartWithTwoLineItems_delete_returnsStatusCodeNoContent() {
    given().port(TEST_PORT).delete(CARTS_PATH).then().statusCode(NO_CONTENT.getStatusCode());
  }

  @Test
  @Order(5)
  void givenAnEmptiedCart_getCart_returnsAnEmptyCart() {
    Response response = given().port(TEST_PORT).get(CARTS_PATH).then().extract().response();

    assertThatResponseIsCart(response, new Cart(TEST_CUSTOMER_ID));
  }
}
