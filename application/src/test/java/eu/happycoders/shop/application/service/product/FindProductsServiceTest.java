package eu.happycoders.shop.application.service.product;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

class FindProductsServiceTest {

  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final FindProductsService findProductsService =
      new FindProductsService(productRepository);

  @Test
  void givenASearchQuery_findByNameOrDescription_returnsTheProductsReturnedByThePersistencePort() {
    when(productRepository.findByNameOrDescription("one")).thenReturn(List.of(TEST_PRODUCT_1));
    when(productRepository.findByNameOrDescription("two")).thenReturn(List.of(TEST_PRODUCT_2));
    when(productRepository.findByNameOrDescription("one-two"))
        .thenReturn(List.of(TEST_PRODUCT_1, TEST_PRODUCT_2));
    when(productRepository.findByNameOrDescription("empty")).thenReturn(List.of());

    assertThat(findProductsService.findByNameOrDescription("one")).containsExactly(TEST_PRODUCT_1);
    assertThat(findProductsService.findByNameOrDescription("two")).containsExactly(TEST_PRODUCT_2);
    assertThat(findProductsService.findByNameOrDescription("one-two"))
        .containsExactly(TEST_PRODUCT_1, TEST_PRODUCT_2);
    assertThat(findProductsService.findByNameOrDescription("empty")).isEmpty();
  }

  @Test
  void givenATooShortSearchQuery_findByNameOrDescription_throwsAnException() {
    String searchQuery = "x";

    ThrowingCallable invocation = () -> findProductsService.findByNameOrDescription(searchQuery);

    assertThatIllegalArgumentException().isThrownBy(invocation);
  }
}
