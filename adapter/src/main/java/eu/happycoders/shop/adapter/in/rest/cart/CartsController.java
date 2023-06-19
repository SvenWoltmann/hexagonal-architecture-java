package eu.happycoders.shop.adapter.in.rest.cart;

import static eu.happycoders.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static eu.happycoders.shop.adapter.in.rest.product.ProductsController.parseProductId;

import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.in.cart.ProductNotFoundException;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.ProductId;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST controller for all shopping cart use cases.
 *
 * @author Sven Woltmann
 */
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class CartsController {

  private final AddToCartUseCase addToCartUseCase;
  private final GetCartUseCase getCartUseCase;
  private final EmptyCartUseCase emptyCartUseCase;

  public CartsController(
      AddToCartUseCase addToCartUseCase,
      GetCartUseCase getCartUseCase,
      EmptyCartUseCase emptyCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
    this.getCartUseCase = getCartUseCase;
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @GET
  @Path("/{customerId}")
  public CartWebModel getCart(@PathParam("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    Cart cart = getCartUseCase.getCart(customerId);
    return CartWebModel.fromDomainModel(cart);
  }

  @POST
  @Path("/{customerId}/line-items")
  public CartWebModel addLineItem(
      @PathParam("customerId") String customerIdString,
      @QueryParam("productId") String productIdString,
      @QueryParam("quantity") int quantity) {
    CustomerId customerId = parseCustomerId(customerIdString);
    ProductId productId = parseProductId(productIdString);

    try {
      Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartWebModel.fromDomainModel(cart);
    } catch (ProductNotFoundException e) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST, "The requested product does not exist");
    } catch (NotEnoughItemsInStockException e) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST, "Only %d items in stock".formatted(e.itemsInStock()));
    }
  }

  @DELETE
  @Path("/{customerId}")
  public Response deleteCart(@PathParam("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    emptyCartUseCase.emptyCart(customerId);
    return Response.noContent().build();
  }

  private static CustomerId parseCustomerId(String string) {
    try {
      return new CustomerId(Integer.parseInt(string));
    } catch (IllegalArgumentException e) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'customerId'");
    }
  }
}
