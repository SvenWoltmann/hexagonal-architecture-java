package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.adapter.out.persistence.AbstractCartRepositoryTest;

class InMemoryCartRepositoryTest
    extends AbstractCartRepositoryTest<InMemoryCartRepository, InMemoryProductRepository> {

  @Override
  protected InMemoryCartRepository createCartRepository() {
    return new InMemoryCartRepository();
  }

  @Override
  protected InMemoryProductRepository createProductPersistenceAdapter() {
    return new InMemoryProductRepository();
  }
}
