package eu.happycoders.shop.application.service.product;

import eu.happycoders.shop.application.port.in.product.GetProductUseCase;
import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;

/**
 * Use case implementation: Retrieving a single product.
 *
 * @author Sven Woltmann
 */
public class GetProductService implements GetProductUseCase {

  private final ProductRepository productRepository;

  public GetProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Optional<Product> getProduct(ProductId productId) {
    return productRepository.findById(productId);
  }
}
