package eu.happycoders.shop.bootstrap;

import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.in.product.FindProductsUseCase;
import eu.happycoders.shop.application.port.out.persistence.CartRepository;
import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.application.service.cart.AddToCartService;
import eu.happycoders.shop.application.service.cart.EmptyCartService;
import eu.happycoders.shop.application.service.cart.GetCartService;
import eu.happycoders.shop.application.service.product.FindProductsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
class QuarkusAppConfig {

  @Inject Instance<CartRepository> cartRepository;

  @Inject Instance<ProductRepository> productRepository;

  @Produces
  @ApplicationScoped
  GetCartUseCase getCartUseCase() {
    return new GetCartService(cartRepository.get());
  }

  @Produces
  @ApplicationScoped
  EmptyCartUseCase emptyCartUseCase() {
    return new EmptyCartService(cartRepository.get());
  }

  @Produces
  @ApplicationScoped
  FindProductsUseCase findProductsUseCase() {
    return new FindProductsService(productRepository.get());
  }

  @Produces
  @ApplicationScoped
  AddToCartUseCase addToCartUseCase() {
    return new AddToCartService(cartRepository.get(), productRepository.get());
  }
}
