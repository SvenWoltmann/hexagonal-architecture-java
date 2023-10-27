package eu.happycoders.shop.adapter.out.persistence.jpa;

import eu.happycoders.shop.adapter.AdapterTestProfileWithMySQL;
import eu.happycoders.shop.adapter.out.persistence.AbstractCartRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(AdapterTestProfileWithMySQL.class)
class JpaCartRepositoryTest extends AbstractCartRepositoryTest {}
