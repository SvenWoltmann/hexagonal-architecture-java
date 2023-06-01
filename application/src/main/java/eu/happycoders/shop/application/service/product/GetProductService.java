package eu.happycoders.shop.application.service.product;

import eu.happycoders.shop.application.port.in.product.GetProductUseCase;
import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;

public class GetProductService implements GetProductUseCase {

  private final ProductPersistencePort productPersistencePort;

  public GetProductService(ProductPersistencePort productPersistencePort) {
    this.productPersistencePort = productPersistencePort;
  }

  @Override
  public Optional<Product> getProduct(ProductId productId) {
    return productPersistencePort.findById(productId);
  }
}
