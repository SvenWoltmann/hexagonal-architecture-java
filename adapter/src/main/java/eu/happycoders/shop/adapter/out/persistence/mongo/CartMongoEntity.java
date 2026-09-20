package eu.happycoders.shop.adapter.out.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * MongoDB document class for a shopping cart.
 *
 * @author Alfredo Rueda & Francisco José Nebrera
 */
@Document(collection = "Cart")
@Getter
@Setter
public class CartMongoEntity {

  @Id private int customerId;

  private List<CartLineItemMongoEntity> lineItems;
}
