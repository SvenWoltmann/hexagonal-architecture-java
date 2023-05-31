package eu.happycoders.shop.model.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerIdTest {
  @ParameterizedTest
  @ValueSource(ints = {-100, -1, 0})
  void givenAValueLessThan1_newCustomerId_throwsException(int value) {
    ThrowableAssert.ThrowingCallable invocation = () -> new CustomerId(value);

    assertThatIllegalArgumentException().isThrownBy(invocation);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 8_765, 2_000_000_000})
  void givenAValueGreatThanOrEqualTo1_newCustomerId_succeeds(int value) {
    CustomerId customerId = new CustomerId(value);

    assertThat(customerId.value()).isEqualTo(value);
  }
}
