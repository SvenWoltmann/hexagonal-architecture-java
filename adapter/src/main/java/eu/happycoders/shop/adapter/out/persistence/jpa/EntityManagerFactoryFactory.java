package eu.happycoders.shop.adapter.out.persistence.jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Map;

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
            "javax.persistence.jdbc.driver",
            "com.mysql.jdbc.Driver",
            "javax.persistence.jdbc.url",
            jdbcUrl,
            "javax.persistence.jdbc.user",
            user,
            "javax.persistence.jdbc.password",
            password));
  }
}
