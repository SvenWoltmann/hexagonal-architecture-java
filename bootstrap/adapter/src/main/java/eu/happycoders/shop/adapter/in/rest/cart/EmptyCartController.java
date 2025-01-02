package eu.happycoders.shop.adapter.in.rest.cart;

import static eu.happycoders.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.model.customer.CustomerId;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST controller for all shopping cart use cases.
 *
 * @author Sven Woltmann
 */
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class EmptyCartController {

  private final EmptyCartUseCase emptyCartUseCase;

  public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @DELETE
  @Path("/{customerId}")
  public void deleteCart(@PathParam("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    emptyCartUseCase.emptyCart(customerId);
  }
}
