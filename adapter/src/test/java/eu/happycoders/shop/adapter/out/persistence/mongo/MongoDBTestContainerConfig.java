package eu.happycoders.shop.adapter.out.persistence.mongo;

import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Profile("test-with-mongodb")
@Testcontainers
public class MongoDBTestContainerConfig {

    // See: https://java.testcontainers.org/test_framework_integration/manual_lifecycle_control/#singleton-containers

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2.8");

    static {
        mongoDBContainer.start();
    }

    public static MongoDBContainer getInstance() {
        return mongoDBContainer;
    }

}