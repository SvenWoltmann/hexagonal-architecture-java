package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.adapter.out.persistence.DemoProducts;
import eu.happycoders.shop.application.port.out.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import io.quarkus.arc.lookup.LookupIfProperty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter: Stores products via JPA in a database.
 *
 * @author Sven Woltmann
 */
@LookupIfProperty(name = "persistence", stringValue = "mysql")
@ApplicationScoped
public class JpaProductRepository implements ProductRepository {

  private final JpaProductPanacheRepository panacheRepository;

  public JpaProductRepository(JpaProductPanacheRepository panacheRepository) {
    this.panacheRepository = panacheRepository;
  }

  @PostConstruct
  void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  @Transactional
  public void save(Product product) {
    panacheRepository.getEntityManager().merge(ProductMapper.toJpaEntity(product));
  }

  @Override
  @Transactional
  public Optional<Product> findById(ProductId productId) {
    ProductJpaEntity jpaEntity = panacheRepository.findById(productId.value());
    return ProductMapper.toModelEntityOptional(jpaEntity);
  }

  @Override
  @Transactional
  public List<Product> findByNameOrDescription(String queryString) {
    List<ProductJpaEntity> entities =
        panacheRepository
            .find("name like ?1 or description like ?1", "%" + queryString + "%")
            .list();

    return ProductMapper.toModelEntities(entities);
  }
}
