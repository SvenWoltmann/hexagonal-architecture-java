package eu.happycoders.shop.adapter.out.persistence.jpa;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Panache repository for {@link ProductJpaEntity}.
 *
 * @author Sven Woltmann
 */
@ApplicationScoped
public class JpaProductPanacheRepository
    implements PanacheRepositoryBase<ProductJpaEntity, String> {}
