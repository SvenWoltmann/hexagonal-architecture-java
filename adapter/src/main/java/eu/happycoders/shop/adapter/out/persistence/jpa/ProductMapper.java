package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

/**
 * Maps a model product to a JPA product and vice versa.
 *
 * @author Sven Woltmann
 */
final class ProductMapper {

  private ProductMapper() {}

  static ProductJpaEntity toJpaEntity(Product product) {
    ProductJpaEntity jpaEntity = new ProductJpaEntity();

    jpaEntity.setId(product.id().value());
    jpaEntity.setName(product.name());
    jpaEntity.setDescription(product.description());
    jpaEntity.setPriceCurrency(product.price().currency().getCurrencyCode());
    jpaEntity.setPriceAmount(product.price().amount());
    jpaEntity.setItemsInStock(product.itemsInStock());

    return jpaEntity;
  }

  static Optional<Product> toModelEntityOptional(ProductJpaEntity jpaEntity) {
    return Optional.ofNullable(jpaEntity).map(ProductMapper::toModelEntity);
  }

  static Product toModelEntity(ProductJpaEntity jpaEntity) {
    return new Product(
        new ProductId(jpaEntity.getId()),
        jpaEntity.getName(),
        jpaEntity.getDescription(),
        new Money(Currency.getInstance(jpaEntity.getPriceCurrency()), jpaEntity.getPriceAmount()),
        jpaEntity.getItemsInStock());
  }

  static List<Product> toModelEntities(List<ProductJpaEntity> jpaEntities) {
    return jpaEntities.stream().map(ProductMapper::toModelEntity).toList();
  }
}
