package eu.happycoders.shop.application.service.product;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.product.Product;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FindProductsServiceTest {

  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private final ProductPersistencePort productPersistencePort = mock(ProductPersistencePort.class);
  private final FindProductsService findProductsService =
      new FindProductsService(productPersistencePort);

  @BeforeEach
  void resetMocks() {
    Mockito.reset(productPersistencePort);
  }

  @Test
  void givenASearchQuery_findByNameOrDescription_returnsTheProductsReturnedByThePersistencePort() {
    when(productPersistencePort.findByNameOrDescription("one")).thenReturn(List.of(TEST_PRODUCT_1));
    when(productPersistencePort.findByNameOrDescription("two")).thenReturn(List.of(TEST_PRODUCT_2));
    when(productPersistencePort.findByNameOrDescription("one-two"))
        .thenReturn(List.of(TEST_PRODUCT_1, TEST_PRODUCT_2));
    when(productPersistencePort.findByNameOrDescription("empty")).thenReturn(List.of());

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
