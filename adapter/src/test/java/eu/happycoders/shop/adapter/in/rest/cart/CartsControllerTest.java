package eu.happycoders.shop.adapter.in.rest.cart;

import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.TEST_PORT;
import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static eu.happycoders.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.in.cart.ProductNotFoundException;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartsControllerTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private static final AddToCartUseCase addToCartUseCase = mock(AddToCartUseCase.class);
  private static final GetCartUseCase getCartUseCase = mock(GetCartUseCase.class);
  private static final EmptyCartUseCase emptyCartUseCase = mock(EmptyCartUseCase.class);

  private static UndertowJaxrsServer server;

  @BeforeAll
  static void init() {
    server =
        new UndertowJaxrsServer()
            .setPort(TEST_PORT)
            .start()
            .deploy(
                new Application() {
                  @Override
                  public Set<Object> getSingletons() {
                    return Set.of(
                        new AddToCartController(addToCartUseCase),
                        new GetCartController(getCartUseCase),
                        new EmptyCartController(emptyCartUseCase));
                  }
                });
  }

  @AfterAll
  static void stop() {
    server.stop();
  }

  @BeforeEach
  void resetMocks() {
    Mockito.reset(addToCartUseCase, getCartUseCase, emptyCartUseCase);
  }

  @Test
  void givenASyntacticallyInvalidCustomerId_getCart_returnsAnError() {
    String customerId = "foo";

    Response response =
        given().port(TEST_PORT).get("/carts/" + customerId).then().extract().response();

    assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'customerId'");
  }

  @Test
  void givenAValidCustomerIdAndACart_getCart_requestsCartFromUseCaseAndReturnsIt()
      throws NotEnoughItemsInStockException {
    CustomerId customerId = TEST_CUSTOMER_ID;

    Cart cart = new Cart(customerId);
    cart.addProduct(TEST_PRODUCT_1, 3);
    cart.addProduct(TEST_PRODUCT_2, 5);

    when(getCartUseCase.getCart(customerId)).thenReturn(cart);

    Response response =
        given().port(TEST_PORT).get("/carts/" + customerId.value()).then().extract().response();

    assertThatResponseIsCart(response, cart);
  }

  @Test
  void givenSomeTestData_addLineItem_invokesAddToCartUseCaseAndReturnsUpdatedCart()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    CustomerId customerId = TEST_CUSTOMER_ID;
    ProductId productId = TEST_PRODUCT_1.id();
    int quantity = 5;

    Cart cart = new Cart(customerId);
    cart.addProduct(TEST_PRODUCT_1, quantity);

    when(addToCartUseCase.addToCart(customerId, productId, quantity)).thenReturn(cart);

    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", productId.value())
            .queryParam("quantity", quantity)
            .post("/carts/" + customerId.value() + "/line-items")
            .then()
            .extract()
            .response();

    assertThatResponseIsCart(response, cart);
  }

  @Test
  void givenAnInvalidProductId_addLineItem_returnsAnError() {
    CustomerId customerId = TEST_CUSTOMER_ID;
    String productId = "";
    int quantity = 5;

    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", productId)
            .queryParam("quantity", quantity)
            .post("/carts/" + customerId.value() + "/line-items")
            .then()
            .extract()
            .response();

    assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'productId'");
  }

  @Test
  void givenProductNotFound_addLineItem_returnsAnError()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    CustomerId customerId = TEST_CUSTOMER_ID;
    ProductId productId = ProductId.randomProductId();
    int quantity = 5;

    when(addToCartUseCase.addToCart(customerId, productId, quantity))
        .thenThrow(new ProductNotFoundException());

    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", productId.value())
            .queryParam("quantity", quantity)
            .post("/carts/" + customerId.value() + "/line-items")
            .then()
            .extract()
            .response();

    assertThatResponseIsError(response, BAD_REQUEST, "The requested product does not exist");
  }

  @Test
  void givenNotEnoughItemsInStock_addLineItem_returnsAnError()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    CustomerId customerId = TEST_CUSTOMER_ID;
    ProductId productId = ProductId.randomProductId();
    int quantity = 5;

    when(addToCartUseCase.addToCart(customerId, productId, quantity))
        .thenThrow(new NotEnoughItemsInStockException("Not enough items in stock", 2));

    Response response =
        given()
            .port(TEST_PORT)
            .queryParam("productId", productId.value())
            .queryParam("quantity", quantity)
            .post("/carts/" + customerId.value() + "/line-items")
            .then()
            .extract()
            .response();

    assertThatResponseIsError(response, BAD_REQUEST, "Only 2 items in stock");
  }

  @Test
  void givenACustomerId_deleteCart_invokesDeleteCartUseCaseAndReturnsUpdatedCart() {
    CustomerId customerId = TEST_CUSTOMER_ID;

    given()
        .port(TEST_PORT)
        .delete("/carts/" + customerId.value())
        .then()
        .statusCode(NO_CONTENT.getStatusCode());

    verify(emptyCartUseCase).emptyCart(customerId);
  }
}
