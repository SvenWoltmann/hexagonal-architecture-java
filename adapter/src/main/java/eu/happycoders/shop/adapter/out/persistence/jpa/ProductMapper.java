package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Currency;
import java.util.List;

/**
 * Maps a model product to a JPA product and vice versa.
 *
 * @author Sven Woltmann
 */
final class ProductMapper {

  private ProductMapper() {}

  static Product toModelEntity(ProductJpaEntity entity) {
    return new Product(
        new ProductId(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        new Money(Currency.getInstance(entity.getPriceCurrency()), entity.getPriceAmount()),
        entity.getItemsInStock());
  }

  static List<Product> toModelEntities(List<ProductJpaEntity> entities) {
    return entities.stream().map(ProductMapper::toModelEntity).toList();
  }

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
}
