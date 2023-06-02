package eu.happycoders.shop.bootstrap;

import eu.happycoders.shop.adapter.in.web.cart.CartsController;
import eu.happycoders.shop.adapter.in.web.product.ProductsController;
import eu.happycoders.shop.adapter.out.persistence.inmemory.InMemoryCartPersistenceAdapter;
import eu.happycoders.shop.adapter.out.persistence.inmemory.InMemoryProductPersistenceAdapter;
import eu.happycoders.shop.adapter.out.persistence.jpa.EntityManagerFactoryFactory;
import eu.happycoders.shop.adapter.out.persistence.jpa.JpaCartPersistenceAdapter;
import eu.happycoders.shop.adapter.out.persistence.jpa.JpaProductPersistenceAdapter;
import eu.happycoders.shop.application.port.in.cart.AddToCartUseCase;
import eu.happycoders.shop.application.port.in.cart.EmptyCartUseCase;
import eu.happycoders.shop.application.port.in.cart.GetCartUseCase;
import eu.happycoders.shop.application.port.in.product.FindProductsUseCase;
import eu.happycoders.shop.application.port.in.product.GetProductUseCase;
import eu.happycoders.shop.application.port.out.persistence.CartPersistencePort;
import eu.happycoders.shop.application.port.out.persistence.ProductPersistencePort;
import eu.happycoders.shop.application.service.cart.AddToCartService;
import eu.happycoders.shop.application.service.cart.EmptyCartService;
import eu.happycoders.shop.application.service.cart.GetCartService;
import eu.happycoders.shop.application.service.product.FindProductsService;
import eu.happycoders.shop.application.service.product.GetProductService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.core.Application;
import java.util.Set;

/**
 * The application configuration for the Undertow server. Evaluates the persistence configuration,
 * instantiates the appropriate adapters and use cases, and wires them.
 *
 * @author Sven Woltmann
 */
public class RestEasyUndertowShopApplication extends Application {

  private CartPersistencePort cartPersistencePort;
  private ProductPersistencePort productPersistencePort;

  // We're encouraged to use "automatic discovery of resources", but I want to define them manually.
  @SuppressWarnings("deprecation")
  @Override
  public Set<Object> getSingletons() {
    initPersistenceAdapters();
    return Set.of(cartController(), productsController());
  }

  private void initPersistenceAdapters() {
    String persistence = System.getProperty("persistence", "inmemory");
    switch (persistence) {
      case "inmemory" -> initInMemoryAdapters();
      case "mysql" -> initMySqlAdapter();
      default -> throw new IllegalArgumentException(
          "Invalid 'persistence' property: '%s' (allowed: 'inmemory', 'mysql')"
              .formatted(persistence));
    }
  }

  private void initInMemoryAdapters() {
    cartPersistencePort = new InMemoryCartPersistenceAdapter();
    productPersistencePort = new InMemoryProductPersistenceAdapter();
  }

  // The EntityManagerFactory doesn't need to get closed before the application is stopped
  @SuppressWarnings("PMD.CloseResource")
  private void initMySqlAdapter() {
    EntityManagerFactory entityManagerFactory =
        EntityManagerFactoryFactory.createMySqlEntityManagerFactory(
            "jdbc:mysql://localhost:3306/shop", "root", "test");

    cartPersistencePort = new JpaCartPersistenceAdapter(entityManagerFactory);
    productPersistencePort = new JpaProductPersistenceAdapter(entityManagerFactory);
  }

  private CartsController cartController() {
    AddToCartUseCase addToCartUseCase =
        new AddToCartService(cartPersistencePort, productPersistencePort);
    GetCartUseCase getCartUseCase = new GetCartService(cartPersistencePort);
    EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartPersistencePort);
    return new CartsController(addToCartUseCase, getCartUseCase, emptyCartUseCase);
  }

  private ProductsController productsController() {
    GetProductUseCase getProductUseCase = new GetProductService(productPersistencePort);
    FindProductsUseCase findProductsUseCase = new FindProductsService(productPersistencePort);
    return new ProductsController(getProductUseCase, findProductsUseCase);
  }
}
