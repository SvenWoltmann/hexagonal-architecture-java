package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.adapter.AdapterTestProfile;
import eu.happycoders.shop.adapter.out.persistence.AbstractProductRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(AdapterTestProfile.class)
class InMemoryProductRepositoryTest extends AbstractProductRepositoryTest {}
