package eu.happycoders.shop.application.service.product;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetProductServiceTest {

  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final GetProductService getProductService = new GetProductService(productRepository);

  @BeforeEach
  void resetMocks() {
    Mockito.reset(productRepository);
  }

  @Test
  void givenAPersistedProduct_getProduct_returnsThatProduct() {
    Product persistedProduct = createTestProduct(euros(19, 99));
    when(productRepository.findById(persistedProduct.id()))
        .thenReturn(Optional.of(persistedProduct));

    Optional<Product> product = getProductService.getProduct(persistedProduct.id());

    assertThat(product).contains(persistedProduct);
  }

  @Test
  void givenAProductDoesNotExist_getProduct_returnsAnEmptyOptional() {
    ProductId productId = ProductId.randomProductId();
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    Optional<Product> product = getProductService.getProduct(productId);

    assertThat(product).isEmpty();
  }
}
