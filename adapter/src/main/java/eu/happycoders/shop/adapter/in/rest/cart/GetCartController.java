package eu.happycoders.shop.adapter.in.rest.cart;

import static eu.happycoders.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for all shopping cart use cases.
 *
 * @author Sven Woltmann
 */
@RestController
@RequestMapping(path = "/carts")
public class GetCartController {

  private final GetCartUseCase getCartUseCase;

  public GetCartController(GetCartUseCase getCartUseCase) {
    this.getCartUseCase = getCartUseCase;
  }

  @GetMapping("/{customerId}")
  public CartWebModel getCart(@PathVariable("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    Cart cart = getCartUseCase.getCart(customerId);
    return CartWebModel.fromDomainModel(cart);
  }
}
