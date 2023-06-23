package eu.happycoders.shop.bootstrap;

import eu.happycoders.shop.adapter.in.rest.cart.AddToCartController;
import eu.happycoders.shop.adapter.in.rest.cart.EmptyCartController;
import eu.happycoders.shop.adapter.in.rest.cart.GetCartController;
import eu.happycoders.shop.adapter.in.rest.product.FindProductsController;
import eu.happycoders.shop.adapter.out.persistence.inmemory.InMemoryCartRepository;
import eu.happycoders.shop.adapter.out.persistence.inmemory.InMemoryProductRepository;
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
import jakarta.ws.rs.core.Application;
import java.util.Set;

/**
 * The application configuration for the Undertow server. Instantiates the adapters and use cases,
 * and wires them.
 *
 * @author Sven Woltmann
 */
public class RestEasyUndertowShopApplication extends Application {

  private CartRepository cartRepository;
  private ProductRepository productRepository;

  // We're encouraged to use "automatic discovery of resources", but I want to define them manually.
  @SuppressWarnings("deprecation")
  @Override
  public Set<Object> getSingletons() {
    initPersistenceAdapters();
    return Set.of(
        addToCartController(),
        getCartController(),
        emptyCartController(),
        findProductsController());
  }

  private void initPersistenceAdapters() {
    cartRepository = new InMemoryCartRepository();
    productRepository = new InMemoryProductRepository();
  }

  private AddToCartController addToCartController() {
    AddToCartUseCase addToCartUseCase = new AddToCartService(cartRepository, productRepository);
    return new AddToCartController(addToCartUseCase);
  }

  private GetCartController getCartController() {
    GetCartUseCase getCartUseCase = new GetCartService(cartRepository);
    return new GetCartController(getCartUseCase);
  }

  private EmptyCartController emptyCartController() {
    EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartRepository);
    return new EmptyCartController(emptyCartUseCase);
  }

  private FindProductsController findProductsController() {
    FindProductsUseCase findProductsUseCase = new FindProductsService(productRepository);
    return new FindProductsController(findProductsUseCase);
  }
}
