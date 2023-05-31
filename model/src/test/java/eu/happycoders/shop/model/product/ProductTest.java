package eu.happycoders.shop.model.product;

import static eu.happycoders.shop.model.cart.TestCartFactory.emptyCartForRandomCustomer;
import static org.assertj.core.api.Assertions.assertThat;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.money.TestMoneyFactory;
import org.junit.jupiter.api.Test;

class ProductTest {

  @Test
  void givenEmptyCart_addToCart_productIsInCart() throws NotEnoughItemsInStockException {
    Cart cart = emptyCartForRandomCustomer();

    Product product = TestProductFactory.createTestProduct(TestMoneyFactory.euros(19, 99));

    product.addToCart(cart, 5);

    assertThat(cart.lineItems()).hasSize(1);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(product);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(5);

    assertThat(cart.numberOfItems()).isEqualTo(5);
    assertThat(cart.subTotal()).isEqualTo(TestMoneyFactory.euros(99, 95));
  }
}
