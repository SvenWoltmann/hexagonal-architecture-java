package eu.happycoders.shop.application.port.out.persistence;

import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.List;
import java.util.Optional;

/**
 * Outgoing persistence port for products.
 *
 * @author Sven Woltmann
 */
public interface ProductPersistencePort {

  void save(Product product);

  Optional<Product> findById(ProductId productId);

  List<Product> findByNameOrDescription(String query);
}
