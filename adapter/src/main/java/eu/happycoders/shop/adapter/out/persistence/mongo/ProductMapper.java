package eu.happycoders.shop.adapter.out.persistence.mongo;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;

import java.util.Currency;
import java.util.List;

/**
 * Maps a model product to a MongoDB product and vice versa.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
final class ProductMapper {

  private ProductMapper() {}

  static ProductMongoEntity toMongoEntity(Product product) {
    ProductMongoEntity mongoEntity = new ProductMongoEntity();

    mongoEntity.setId(product.id().value());
    mongoEntity.setName(product.name());
    mongoEntity.setDescription(product.description());
    mongoEntity.setPriceCurrency(product.price().currency().getCurrencyCode());
    mongoEntity.setPriceAmount(product.price().amount());
    mongoEntity.setItemsInStock(product.itemsInStock());

    return mongoEntity;
  }

  static Product toModelEntity(ProductMongoEntity mongoEntity) {
    return new Product(
        new ProductId(mongoEntity.getId()),
        mongoEntity.getName(),
        mongoEntity.getDescription(),
        new Money(
            Currency.getInstance(mongoEntity.getPriceCurrency()), mongoEntity.getPriceAmount()),
        mongoEntity.getItemsInStock());
  }

  static List<Product> toModelEntities(List<ProductMongoEntity> mongoEntities) {
    return mongoEntities.stream().map(ProductMapper::toModelEntity).toList();
  }
}
