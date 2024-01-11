package eu.happycoders.shop.adapter.in.rest.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles {@link ClientErrorException} by returning a JSON body containing the error details.
 *
 * @author Sven Woltmann
 */
@RestControllerAdvice
public class ClientErrorHandler {

  @ExceptionHandler(ClientErrorException.class)
  public ResponseEntity<ErrorEntity> handleProductNotFoundException(ClientErrorException ex) {
    return ex.getResponse();
  }
}
