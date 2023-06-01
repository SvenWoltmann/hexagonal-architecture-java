package eu.happycoders.shop.adapter.out.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @Id @GeneratedValue private Integer id;

  @ManyToOne private CartJpaEntity cart;

  @ManyToOne private ProductJpaEntity product;

  private int quantity;
}
