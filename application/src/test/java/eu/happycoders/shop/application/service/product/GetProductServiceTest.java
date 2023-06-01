package eu.happycoders.shop.application.service.product;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetProductServiceTest {

  private final ProductPersistencePort productPersistencePort = mock(ProductPersistencePort.class);
  private final GetProductService getProductService = new GetProductService(productPersistencePort);

  @BeforeEach
  void resetMocks() {
    Mockito.reset(productPersistencePort);
  }

  @Test
  void givenAPersistedProduct_getProduct_returnsThatProduct() {
    Product persistedProduct = createTestProduct(euros(19, 99));
    when(productPersistencePort.findById(persistedProduct.id()))
        .thenReturn(Optional.of(persistedProduct));

    Optional<Product> product = getProductService.getProduct(persistedProduct.id());

    assertThat(product).isNotEmpty();
    assertThat(product.get()).isEqualTo(persistedProduct);
  }

  @Test
  void givenAProductDoesNotExist_getProduct_returnsAnEmptyOptional() {
    ProductId productId = ProductId.randomProductId();
    when(productPersistencePort.findById(productId)).thenReturn(Optional.empty());

    Optional<Product> product = getProductService.getProduct(productId);

    assertThat(product).isEmpty();
  }
}
