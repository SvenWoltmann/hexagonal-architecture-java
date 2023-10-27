package eu.happycoders.shop.adapter;

import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.in.product.FindProductsUseCase;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@IfBuildProfile(anyOf = {AdapterTestProfile.PROFILE_NAME, AdapterTestProfileWithMySQL.PROFILE_NAME})
class QuarkusAppConfig {

  // We need to define these beans, even though they will be mocked in the tests.
  // (they are from the "application" module and have no @ApplicationScoped annotation.)

  @Produces
  @ApplicationScoped
  GetCartUseCase getCartUseCase() {
    return customerId -> {
      throw new UnsupportedOperationException("This bean should be mocked in tests");
    };
  }

  @Produces
  @ApplicationScoped
  EmptyCartUseCase emptyCartUseCase() {
    return customerId -> {
      throw new UnsupportedOperationException("This bean should be mocked in tests");
    };
  }

  @Produces
  @ApplicationScoped
  FindProductsUseCase findProductsUseCase() {
    return query -> {
      throw new UnsupportedOperationException("This bean should be mocked in tests");
    };
  }

  @Produces
  @ApplicationScoped
  AddToCartUseCase addToCartUseCase() {
    return (customerId, productId, quantity) -> {
      throw new UnsupportedOperationException("This bean should be mocked in tests");
    };
  }
}
