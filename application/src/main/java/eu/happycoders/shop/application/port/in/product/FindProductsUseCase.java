package eu.happycoders.shop.application.port.in.product;

import eu.happycoders.shop.model.product.Product;
import java.util.List;

public interface FindProductsUseCase {

  List<Product> findByNameOrDescription(String query);
}
