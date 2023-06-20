package eu.happycoders.shop.application.service.cart;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.model.customer.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmptyCartServiceTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final EmptyCartService emptyCartService = new EmptyCartService(cartRepository);

  @BeforeEach
  void resetMocks() {
    Mockito.reset(cartRepository);
  }

  @Test
  void emptyCart_invokesDeleteOnThePersistencePort() {
    emptyCartService.emptyCart(TEST_CUSTOMER_ID);

    verify(cartRepository).deleteById(TEST_CUSTOMER_ID);
  }
}
