package eu.happycoders.shop.adapter.out.persistence.jpa;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link ProductJpaEntity}.
 *
 * @author Sven Woltmann
 */
@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
public interface JpaProductSpringDataRepository extends JpaRepository<ProductJpaEntity, String> {

  @Query("SELECT p FROM ProductJpaEntity p WHERE p.name like ?1 or p.description like ?1")
  List<ProductJpaEntity> findByNameOrDescriptionLike(String pattern);
}
