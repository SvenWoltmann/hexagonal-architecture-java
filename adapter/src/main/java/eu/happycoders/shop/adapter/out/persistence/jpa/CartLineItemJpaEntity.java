package eu.happycoders.shop.adapter.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity class for a shopping cart line item.
 *
 * @author Sven Woltmann
 */
@Entity
@Table(name = "CartLineItem")
@Getter
@Setter
public class CartLineItemJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne private CartJpaEntity cart;

  @ManyToOne private ProductJpaEntity product;

  private int quantity;
}
