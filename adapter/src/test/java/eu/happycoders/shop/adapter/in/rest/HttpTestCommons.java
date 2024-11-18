package eu.happycoders.shop.adapter.in.rest;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public final class HttpTestCommons {

  private HttpTestCommons() {}

  public static void assertThatResponseIsError(
      Response response,
      jakarta.ws.rs.core.Response.Status expectedStatus,
      String expectedErrorMessage) {
    assertThat(response.getStatusCode()).isEqualTo(expectedStatus.getStatusCode());

    JsonPath json = response.jsonPath();

    assertThat(json.getInt("httpStatus")).isEqualTo(expectedStatus.getStatusCode());
    assertThat(json.getString("errorMessage")).isEqualTo(expectedErrorMessage);
  }
}
