package eu.happycoders.shop.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link CartJpaEntity}.
 *
 * @author Sven Woltmann
 */
@Repository
public interface JpaCartSpringDataRepository extends JpaRepository<CartJpaEntity, Integer> {}
