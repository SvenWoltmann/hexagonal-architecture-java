package eu.happycoders.shop.adapter.in.rest.common;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * An exception to be thrown in case of a client error (e.g., invalid input).
 *
 * @author Sven Woltmann
 */
public class ClientErrorException extends RuntimeException {

  @Getter private final ResponseEntity<ErrorEntity> response;

  public ClientErrorException(ResponseEntity<ErrorEntity> response) {
    super(getMessage(response));
    this.response = response;
  }

  private static String getMessage(ResponseEntity<ErrorEntity> response) {
    ErrorEntity body = response.getBody();
    return body != null ? body.errorMessage() : null;
  }
}
