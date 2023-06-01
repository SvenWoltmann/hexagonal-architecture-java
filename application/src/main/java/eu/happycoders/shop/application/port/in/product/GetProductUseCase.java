package eu.happycoders.shop.application.port.in.product;

import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;

/**
 * Use case: Retrieving a single product.
 *
 * @author Sven Woltmann
 */
public interface GetProductUseCase {

  Optional<Product> getProduct(ProductId productId);
}
