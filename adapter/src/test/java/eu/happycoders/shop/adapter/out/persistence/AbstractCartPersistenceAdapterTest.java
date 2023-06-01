package eu.happycoders.shop.adapter.out.persistence;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;

import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractCartPersistenceAdapterTest<
    T extends CartPersistencePort, U extends ProductPersistencePort> {

  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(1, 49));

  private static final AtomicInteger CUSTOMER_ID_SEQUENCE_GENERATOR = new AtomicInteger();

  protected T persistenceAdapter;

  @BeforeEach
  void resetPersistenceAdapter() {
    persistenceAdapter = createPersistenceAdapter();
    persistTestProducts();
  }

  protected abstract T createPersistenceAdapter();

  private void persistTestProducts() {
    U productPersistenceAdapter = createProductPersistenceAdapter();
    productPersistenceAdapter.save(TEST_PRODUCT_1);
    productPersistenceAdapter.save(TEST_PRODUCT_2);
  }

  protected abstract U createProductPersistenceAdapter();

  @Test
  void givenACustomerIdForWhichNoCartIsPersisted_findByCustomerId_returnsAnEmptyOptional() {
    CustomerId customerId = createUniqueCustomerId();

    Optional<Cart> cart = persistenceAdapter.findByCustomerId(customerId);

    assertThat(cart).isEmpty();
  }

  @Test
  void givenPersistedCartWithProduct_findByCustomerId_returnsTheAppropriateCart()
      throws NotEnoughItemsInStockException {
    CustomerId customerId = createUniqueCustomerId();

    Cart persistedCart = new Cart(customerId);
    persistedCart.addProduct(TEST_PRODUCT_1, 1);
    persistenceAdapter.save(persistedCart);

    Optional<Cart> cart = persistenceAdapter.findByCustomerId(customerId);

    assertThat(cart).isNotEmpty();
    assertThat(cart.get().id()).isEqualTo(customerId);
    assertThat(cart.get().lineItems()).hasSize(1);
    assertThat(cart.get().lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.get().lineItems().get(0).quantity()).isEqualTo(1);
  }

  @Test
  void
      givenExistingCartWithProduct_andGivenANewCartForTheSameCustomer_saveCart_overwritesTheExistingCart()
          throws NotEnoughItemsInStockException {
    CustomerId customerId = createUniqueCustomerId();

    Cart existingCart = new Cart(customerId);
    existingCart.addProduct(TEST_PRODUCT_1, 1);
    persistenceAdapter.save(existingCart);

    Cart newCart = new Cart(customerId);
    newCart.addProduct(TEST_PRODUCT_2, 2);
    persistenceAdapter.save(newCart);

    Optional<Cart> cart = persistenceAdapter.findByCustomerId(customerId);
    assertThat(cart).isNotEmpty();
    assertThat(cart.get().id()).isEqualTo(customerId);
    assertThat(cart.get().lineItems()).hasSize(1);
    assertThat(cart.get().lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_2);
    assertThat(cart.get().lineItems().get(0).quantity()).isEqualTo(2);
  }

  @Test
  void givenExistingCartWithProduct_addProductAndSaveCart_updatesTheExistingCart()
      throws NotEnoughItemsInStockException {
    CustomerId customerId = createUniqueCustomerId();

    Cart existingCart = new Cart(customerId);
    existingCart.addProduct(TEST_PRODUCT_1, 1);
    persistenceAdapter.save(existingCart);

    existingCart = persistenceAdapter.findByCustomerId(customerId).orElseThrow();
    existingCart.addProduct(TEST_PRODUCT_2, 2);
    persistenceAdapter.save(existingCart);

    Optional<Cart> cart = persistenceAdapter.findByCustomerId(customerId);
    assertThat(cart).isNotEmpty();
    assertThat(cart.get().id()).isEqualTo(customerId);
    assertThat(cart.get().lineItems())
        .map(CartLineItem::product)
        .containsExactlyInAnyOrder(TEST_PRODUCT_1, TEST_PRODUCT_2);
  }

  @Test
  void givenExistingCart_deleteById_deletesTheCart() {
    CustomerId customerId = createUniqueCustomerId();

    Cart existingCart = new Cart(customerId);
    persistenceAdapter.save(existingCart);

    assertThat(persistenceAdapter.findByCustomerId(customerId)).isNotEmpty();

    persistenceAdapter.deleteById(customerId);

    assertThat(persistenceAdapter.findByCustomerId(customerId)).isEmpty();
  }

  @Test
  void givenNotExistingCart_deleteById_doesNothing() {
    CustomerId customerId = createUniqueCustomerId();
    assertThat(persistenceAdapter.findByCustomerId(customerId)).isEmpty();

    persistenceAdapter.deleteById(customerId);

    assertThat(persistenceAdapter.findByCustomerId(customerId)).isEmpty();
  }

  private static CustomerId createUniqueCustomerId() {
    return new CustomerId(CUSTOMER_ID_SEQUENCE_GENERATOR.incrementAndGet());
  }
}
