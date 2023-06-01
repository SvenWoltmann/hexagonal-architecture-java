package eu.happycoders.shop.adapter.out.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import eu.happycoders.shop.adapter.out.persistence.AbstractCartPersistenceAdapterTest;

class InMemoryCartPersistenceAdapterTest
    extends AbstractCartPersistenceAdapterTest<
        InMemoryCartPersistenceAdapter, InMemoryProductPersistenceAdapter> {

  @Override
  protected InMemoryCartPersistenceAdapter createPersistenceAdapter() {
    return new InMemoryCartPersistenceAdapter();
  }

  @Override
  protected InMemoryProductPersistenceAdapter createProductPersistenceAdapter() {
    return new InMemoryProductPersistenceAdapter();
  }
}
