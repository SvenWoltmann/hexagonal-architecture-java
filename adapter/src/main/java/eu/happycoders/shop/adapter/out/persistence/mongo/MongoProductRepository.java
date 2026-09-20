package eu.happycoders.shop.adapter.out.persistence.mongo;

import eu.happycoders.shop.adapter.out.persistence.DemoProducts;
import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter: Stores products via MongoDB.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@ConditionalOnProperty(name = "persistence", havingValue = "mongodb")
@Repository
public class MongoProductRepository implements ProductRepository {

  private final MongoProductSpringDataRepository springDataRepository;

  public MongoProductRepository(MongoProductSpringDataRepository springDataRepository) {
    this.springDataRepository = springDataRepository;
  }

  @PostConstruct
  void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  @Transactional
  public void save(Product product) {
    springDataRepository.save(ProductMapper.toMongoEntity(product));
  }

  @Override
  @Transactional
  public Optional<Product> findById(ProductId productId) {
    Optional<ProductMongoEntity> mongoEntity = springDataRepository.findById(productId.value());
    return mongoEntity.map(ProductMapper::toModelEntity);
  }

  @Override
  @Transactional
  public List<Product> findByNameOrDescription(String queryString) {
    List<ProductMongoEntity> entities =
        springDataRepository.findByNameOrDescriptionLike(queryString);

    return ProductMapper.toModelEntities(entities);
  }
}
