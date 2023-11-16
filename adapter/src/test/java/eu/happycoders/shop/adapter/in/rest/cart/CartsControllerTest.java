package eu.happycoders.shop.adapter.in.rest.cart;

import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static eu.happycoders.shop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartsControllerTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  @LocalServerPort private Integer port;

  @MockBean AddToCartUseCase addToCartUseCase;
  @MockBean GetCartUseCase getCartUseCase;
  @MockBean EmptyCartUseCase emptyCartUseCase;

  @Test
  void givenASyntacticallyInvalidCustomerId_getCart_returnsAnError() {
    String customerId = "foo";

    Response response = given().port(port).get("/carts/" + customerId).then().extract().response();

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
        given().port(port).get("/carts/" + customerId.value()).then().extract().response();

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
            .port(port)
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
            .port(port)
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
            .port(port)
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
            .port(port)
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

    given().port(port).delete("/carts/" + customerId.value()).then().statusCode(NO_CONTENT.value());

    verify(emptyCartUseCase).emptyCart(customerId);
  }
}
