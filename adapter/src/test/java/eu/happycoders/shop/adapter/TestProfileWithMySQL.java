package eu.happycoders.shop.adapter;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class TestProfileWithMySQL implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of("persistence", "mysql");
  }
}
