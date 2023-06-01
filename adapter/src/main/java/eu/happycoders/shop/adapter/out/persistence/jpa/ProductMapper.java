package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.Currency;
import java.util.List;

final class ProductMapper {

  private ProductMapper() {}

  static Product toModelEntity(ProductJpaEntity entity) {
    return new Product(
        new ProductId(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        Money.ofMinor(
            Currency.getInstance(entity.getPriceCurrency()), entity.getPriceAmountInMinorUnit()),
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
    jpaEntity.setPriceAmountInMinorUnit(product.price().amountInMinorUnit());
    jpaEntity.setPriceAmount(product.price().amount());
    jpaEntity.setItemsInStock(product.itemsInStock());

    return jpaEntity;
  }
}
