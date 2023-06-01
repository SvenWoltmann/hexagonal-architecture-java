package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.adapter.out.persistence.AbstractCartPersistenceAdapterTest;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

class JpaCartPersistenceAdapterTest
    extends AbstractCartPersistenceAdapterTest<
        JpaCartPersistenceAdapter, JpaProductPersistenceAdapter> {

  private static MySQLContainer<?> mysql;
  private static EntityManagerFactory entityManagerFactory;

  @BeforeAll
  static void startDatabase() {
    mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));
    mysql.start();

    entityManagerFactory =
        EntityManagerFactoryFactory.createMySqlEntityManagerFactory(
            mysql.getJdbcUrl(), "root", "test");
  }

  @Override
  protected JpaCartPersistenceAdapter createPersistenceAdapter() {
    return new JpaCartPersistenceAdapter(entityManagerFactory);
  }

  @Override
  protected JpaProductPersistenceAdapter createProductPersistenceAdapter() {
    return new JpaProductPersistenceAdapter(entityManagerFactory);
  }

  @AfterAll
  static void stopDatabase() {
    entityManagerFactory.close();
    mysql.stop();
  }
}
