package eu.happycoders.shop.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public abstract class AbstractProductPersistenceAdapterTest<T extends ProductPersistencePort> {

  protected T productPersistenceAdapter;

  @Test
  void givenTestProductsAndATestProductId_findById_returnsATestProduct() {
    ProductId productId = DemoProducts.COMPUTER_MONITOR.id();

    Optional<Product> product = productPersistenceAdapter.findById(productId);

    assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
  }

  @Test
  void givenTheIdOfAProductNotPersisted_findById_returnsAnEmptyOptional() {
    ProductId productId = new ProductId("00000");

    Optional<Product> product = productPersistenceAdapter.findById(productId);

    assertThat(product).isEmpty();
  }

  @Test
  void givenTestProductsAndASearchQueryNotMatchingAndProduct_findById_returnsAnEmptyList() {
    String query = "not matching any product";

    List<Product> products = productPersistenceAdapter.findByNameOrDescription(query);

    assertThat(products).isEmpty();
  }

  @Test
  void givenTestProductsAndASearchQueryMatchingOneProduct_findById_returnsThatProduct() {
    String query = "lights";

    List<Product> products = productPersistenceAdapter.findByNameOrDescription(query);

    assertThat(products).containsExactlyInAnyOrder(DemoProducts.LED_LIGHTS);
  }

  @Test
  void givenTestProductsAndASearchQueryMatchingTwoProducts_findById_returnsThoseProducts() {
    String query = "monitor";

    List<Product> products = productPersistenceAdapter.findByNameOrDescription(query);

    assertThat(products)
        .containsExactlyInAnyOrder(DemoProducts.COMPUTER_MONITOR, DemoProducts.MONITOR_DESK_MOUNT);
  }
}
