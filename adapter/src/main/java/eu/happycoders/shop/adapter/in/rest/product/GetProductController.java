package eu.happycoders.shop.adapter.in.rest.product;

import static eu.happycoders.shop.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static eu.happycoders.shop.adapter.in.rest.common.ProductIdParser.parseProductId;

import eu.happycoders.shop.application.port.in.product.GetProductUseCase;
import eu.happycoders.shop.model.product.ProductId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST controller for all product use cases.
 *
 * @author Sven Woltmann
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class GetProductController {

  private final GetProductUseCase getProductUseCase;

  public GetProductController(GetProductUseCase getProductUseCase) {
    this.getProductUseCase = getProductUseCase;
  }

  @GET
  @Path("/{productId}")
  public ProductWebModel getProduct(@PathParam("productId") String productIdString) {
    ProductId productId = parseProductId(productIdString);
    return getProductUseCase
        .getProduct(productId)
        .map(ProductWebModel::fromDomainModel)
        .orElseThrow(() -> clientErrorException(Response.Status.NOT_FOUND, "Product not found"));
  }
}
