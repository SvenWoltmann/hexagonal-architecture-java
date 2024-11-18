package eu.happycoders.shop.adapter.in.rest.product;

import static eu.happycoders.shop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static eu.happycoders.shop.adapter.in.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList;
import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.in.product.FindProductsUseCase;
import eu.happycoders.shop.model.product.Product;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ProductsControllerTest {

  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  @InjectMock FindProductsUseCase findProductsUseCase;

  @Test
  void givenAQueryAndAListOfProducts_findProducts_requestsProductsViaQueryAndReturnsThem() {
    String query = "foo";
    List<Product> productList = List.of(TEST_PRODUCT_1, TEST_PRODUCT_2);

    when(findProductsUseCase.findByNameOrDescription(query)).thenReturn(productList);

    Response response =
        given().queryParam("query", query).get("/products").then().extract().response();

    assertThatResponseIsProductList(response, productList);
  }

  @Test
  void givenANullQuery_findProducts_returnsError() {
    Response response = given().get("/products").then().extract().response();

    assertThatResponseIsError(response, BAD_REQUEST, "Missing 'query'");
  }

  @Test
  void givenATooShortQuery_findProducts_returnsError() {
    String query = "e";
    when(findProductsUseCase.findByNameOrDescription(query))
        .thenThrow(IllegalArgumentException.class);

    Response response =
        given().queryParam("query", query).get("/products").then().extract().response();

    assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'query'");
  }
}
