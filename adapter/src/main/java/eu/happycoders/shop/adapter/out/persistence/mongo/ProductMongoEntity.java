package eu.happycoders.shop.adapter.out.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * MongoDB document class for a product.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@Document(collection = "Product")
@Getter
@Setter
public class ProductMongoEntity {

  @Id private String id;

  @Field("name")
  private String name;

  @Field("description")
  private String description;

  @Field("price_currency")
  private String priceCurrency;

  @Field("price_amount")
  private BigDecimal priceAmount;

  @Field("items_in_stock")
  private int itemsInStock;
}
