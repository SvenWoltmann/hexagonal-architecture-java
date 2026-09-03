package eu.happycoders.shop.bootstrap.e2e;

import static eu.happycoders.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.LED_LIGHTS;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.MONITOR_DESK_MOUNT;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-with-mysql")
// Uncomment the following line to run the tests with MongoDB (and comment the previous line)
//@ActiveProfiles("test-with-mongodb")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final String CARTS_PATH = "/carts/" + TEST_CUSTOMER_ID.value();

  @LocalServerPort private Integer port;

  @Test
  @Order(1)
  void givenAnEmptyCart_addLineItem_addsTheLineItemAndReturnsTheCartWithTheAddedItem()
      throws NotEnoughItemsInStockException {
    Response response =
        given()
            .port(port)
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
            .port(port)
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
    Response response = given().port(port).get(CARTS_PATH).then().extract().response();

    Cart expectedCart = new Cart(TEST_CUSTOMER_ID);
    expectedCart.addProduct(LED_LIGHTS, 3);
    expectedCart.addProduct(MONITOR_DESK_MOUNT, 1);

    assertThatResponseIsCart(response, expectedCart);
  }

  @Test
  @Order(4)
  void givenACartWithTwoLineItems_delete_returnsStatusCodeNoContent() {
    given().port(port).delete(CARTS_PATH).then().statusCode(NO_CONTENT.value());
  }

  @Test
  @Order(5)
  void givenAnEmptiedCart_getCart_returnsAnEmptyCart() {
    Response response = given().port(port).get(CARTS_PATH).then().extract().response();

    assertThatResponseIsCart(response, new Cart(TEST_CUSTOMER_ID));
  }
}
