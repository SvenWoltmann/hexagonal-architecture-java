package eu.happycoders.shop.adapter;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class AdapterTestProfileWithMySQL implements QuarkusTestProfile {

  static final String PROFILE_NAME = "AdapterTestProfileWithMySQL";

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of("persistence", "mysql");
  }

  @Override
  public String getConfigProfile() {
    return PROFILE_NAME;
  }
}
