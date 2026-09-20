package eu.happycoders.shop.adapter.out.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB document class for a shopping cart line item.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@Document(collection = "CartLineItem")
@Getter
@Setter
public class CartLineItemMongoEntity {

  @DBRef private ProductMongoEntity product;

  private int quantity;
}
