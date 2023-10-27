package eu.happycoders.shop.adapter.out.persistence.inmemory;

import eu.happycoders.shop.adapter.AdapterTestProfile;
import eu.happycoders.shop.adapter.out.persistence.AbstractCartRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(AdapterTestProfile.class)
class InMemoryCartRepositoryTest extends AbstractCartRepositoryTest {}
