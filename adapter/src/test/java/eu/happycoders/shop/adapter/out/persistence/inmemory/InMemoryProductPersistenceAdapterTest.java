package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.adapter.out.persistence.AbstractProductPersistenceAdapterTest;
import org.junit.jupiter.api.BeforeEach;

class InMemoryProductPersistenceAdapterTest
    extends AbstractProductPersistenceAdapterTest<InMemoryProductPersistenceAdapter> {

  @BeforeEach
  void setUpPersistenceAdapter() {
    productPersistenceAdapter = new InMemoryProductPersistenceAdapter();
  }
}
