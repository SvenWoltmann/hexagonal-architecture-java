package eu.happycoders.shop.application.service.cart;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.happycoders.shop.application.port.in.cart.ProductNotFoundException;
import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddToCartServiceTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final AddToCartService addToCartService =
      new AddToCartService(cartRepository, productRepository);

  @BeforeEach
  void initTestDoubles() {
    when(productRepository.findById(TEST_PRODUCT_1.id())).thenReturn(Optional.of(TEST_PRODUCT_1));

    when(productRepository.findById(TEST_PRODUCT_2.id())).thenReturn(Optional.of(TEST_PRODUCT_2));
  }

  @Test
  void givenExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
    persistedCart.addProduct(TEST_PRODUCT_1, 1);

    when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

    Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.id(), 3);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(2);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(1);
    assertThat(cart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_2);
    assertThat(cart.lineItems().get(1).quantity()).isEqualTo(3);
  }

  @Test
  void givenNoExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), 2);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(1);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(2);
  }

  @Test
  void givenAnUnknownProductId_addToCart_throwsException() {
    ProductId productId = ProductId.randomProductId();

    ThrowingCallable invocation = () -> addToCartService.addToCart(TEST_CUSTOMER_ID, productId, 1);

    assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }

  @Test
  void givenQuantityLessThan1_addToCart_throwsException() {
    int quantity = 0;

    ThrowingCallable invocation =
        () -> addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), quantity);

    assertThatIllegalArgumentException().isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }
}
