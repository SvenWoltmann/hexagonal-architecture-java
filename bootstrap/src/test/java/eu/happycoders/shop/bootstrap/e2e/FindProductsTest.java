package eu.happycoders.shop.bootstrap.e2e;

import static eu.happycoders.shop.adapter.in.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.COMPUTER_MONITOR;
import static eu.happycoders.shop.adapter.out.persistence.DemoProducts.MONITOR_DESK_MOUNT;
import static io.restassured.RestAssured.given;

import eu.happycoders.shop.adapter.TestProfileWithMySQL;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(TestProfileWithMySQL.class)
class FindProductsTest {

  @Test
  void givenTestProductsAndAQuery_findProducts_returnsMatchingProducts() {
    String query = "monitor";

    Response response =
        given().queryParam("query", query).get("/products").then().extract().response();

    assertThatResponseIsProductList(response, List.of(COMPUTER_MONITOR, MONITOR_DESK_MOUNT));
  }
}
