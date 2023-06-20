package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.adapter.out.persistence.AbstractProductRepositoryTest;
import org.junit.jupiter.api.BeforeEach;

class InMemoryProductRepositoryTest
    extends AbstractProductRepositoryTest<InMemoryProductRepository> {

  @BeforeEach
  void setUpPersistenceAdapter() {
    productRepository = new InMemoryProductRepository();
  }
}
