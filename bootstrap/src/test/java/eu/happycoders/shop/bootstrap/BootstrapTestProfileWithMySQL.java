package eu.happycoders.shop.bootstrap;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class BootstrapTestProfileWithMySQL implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of("persistence", "mysql");
  }
}
