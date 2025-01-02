package eu.happycoders.shop.adapter.out.persistence.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Map;

/**
 * Factory for an <code>EntityManagerFactory</code> for connecting to a MySQL database.
 *
 * @author Sven Woltmann
 */
public final class EntityManagerFactoryFactory {

  private EntityManagerFactoryFactory() {}

  public static EntityManagerFactory createMySqlEntityManagerFactory(
      String jdbcUrl, String user, String password) {
    return Persistence.createEntityManagerFactory(
        "eu.happycoders.shop.adapter.out.persistence.jpa",
        Map.of(
            "hibernate.dialect",
            "org.hibernate.dialect.MySQLDialect",
            "hibernate.hbm2ddl.auto",
            "update",
            "jakarta.persistence.jdbc.driver",
            "com.mysql.jdbc.Driver",
            "jakarta.persistence.jdbc.url",
            jdbcUrl,
            "jakarta.persistence.jdbc.user",
            user,
            "jakarta.persistence.jdbc.password",
            password));
  }
}
