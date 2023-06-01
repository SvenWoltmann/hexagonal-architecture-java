package eu.happycoders.shop.adapter.in.web.product;

import static eu.happycoders.shop.adapter.in.web.common.ControllerCommons.clientErrorException;

import eu.happycoders.shop.application.port.in.product.FindProductsUseCase;
import eu.happycoders.shop.application.port.in.product.GetProductUseCase;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsController {

  private final GetProductUseCase getProductUseCase;
  private final FindProductsUseCase findProductsUseCase;

  public ProductsController(
      GetProductUseCase getProductUseCase, FindProductsUseCase findProductsUseCase) {
    this.getProductUseCase = getProductUseCase;
    this.findProductsUseCase = findProductsUseCase;
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

  @GET
  public List<ProductInListWebModel> findProducts(@QueryParam("query") String query) {
    if (query == null) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Missing 'query'");
    }

    List<Product> products;

    try {
      products = findProductsUseCase.findByNameOrDescription(query);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'query'");
    }

    return products.stream().map(ProductInListWebModel::fromDomainModel).toList();
  }

  public static ProductId parseProductId(String string) {
    if (string == null) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Missing 'productId'");
    }

    try {
      return new ProductId(string);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'productId'");
    }
  }
}
