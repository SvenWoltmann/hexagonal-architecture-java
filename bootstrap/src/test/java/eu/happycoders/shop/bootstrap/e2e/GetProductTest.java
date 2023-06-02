package eu.happycoders.shop.bootstrap.e2e;

import static eu.happycoders.shop.adapter.in.web.HttpTestCommons.TEST_PORT;
import static eu.happycoders.shop.adapter.in.web.product.ProductsControllerAssertions.assertThatResponseIsProduct;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.PLASTIC_SHEETING;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

class GetProductTest extends EndToEndTest {

  @Test
  void givenATestProduct_getProduct_returnsTheProduct() {
    Response response =
        given()
            .port(TEST_PORT)
            .get("/products/" + PLASTIC_SHEETING.id().value())
            .then()
            .extract()
            .response();

    assertThatResponseIsProduct(response, PLASTIC_SHEETING);
  }
}
